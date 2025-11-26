package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AtualizarClienteRequestDTO;
import com.comy_delivery_back.dto.request.AtualizarEnderecoRequestDTO;
import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.*;
import com.comy_delivery_back.enums.TipoEndereco;
import com.comy_delivery_back.exception.ClienteNaoEncontradoException;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.exception.RegistrosDuplicadosException;
import com.comy_delivery_back.exception.RegraDeNegocioException;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.ClienteRepository;
import com.comy_delivery_back.repository.EnderecoRepository;
import com.comy_delivery_back.repository.ProdutoRepository;
import com.comy_delivery_back.repository.RestauranteRepository;
import com.comy_delivery_back.utils.DistanciaUtils;
import com.comy_delivery_back.utils.FreteUtils;
import com.comy_delivery_back.utils.TempoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;//usar depois
    private final EmailService emailService;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    @Value("${app.password-recovery.url}")
    private String passwordRecoveryUrl;

    public ClienteService(ClienteRepository clienteRepository,
                          EnderecoRepository enderecoRepository, EnderecoService enderecoService, PasswordEncoder passwordEncoder,
                          EmailService emailService, RestauranteRepository restauranteRepository, ProdutoRepository produtoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.enderecoService = enderecoService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        log.info("A iniciar cadastro de novo cliente: {}", clienteRequestDTO.emailCliente());
        if (clienteRepository.findByCpfCliente(clienteRequestDTO.cpfCliente()).isPresent()) {
            throw new RegistrosDuplicadosException("CPF já cadastrado!");
        }

        if (clienteRepository.findByEmailCliente(clienteRequestDTO.emailCliente()).isPresent()) {
            throw new RegistrosDuplicadosException("EMAIL já cadastrado!");
        }

        Cliente novoCliente = new Cliente();

        novoCliente.setUsername(clienteRequestDTO.username());
        novoCliente.setPassword(passwordEncoder.encode(clienteRequestDTO.password()));
        novoCliente.setNmCliente(clienteRequestDTO.nmCliente());
        novoCliente.setCpfCliente(clienteRequestDTO.cpfCliente());
        novoCliente.setEmailCliente(clienteRequestDTO.emailCliente());
        novoCliente.setTelefoneCliente(clienteRequestDTO.telefoneCliente());

        novoCliente.setEnderecos(new ArrayList<>());
        Cliente clienteSalvo = clienteRepository.save(novoCliente);

        if (clienteRequestDTO.enderecos() != null && !clienteRequestDTO.enderecos().isEmpty()) {
            boolean primeiroEndereco = true;

            for (EnderecoRequestDTO enderecoDTO : clienteRequestDTO.enderecos()) {
                EnderecoResponseDTO enderecoSalvoDTO = enderecoService.cadastrarEndereco(enderecoDTO);

                Endereco enderecoEntity = enderecoRepository.findByIdEndereco(enderecoSalvoDTO.idEndereco())
                        .orElseThrow(() -> new EnderecoNaoEncontradoException(enderecoSalvoDTO.idEndereco()));

                enderecoEntity.setCliente(clienteSalvo);
                if (primeiroEndereco) {
                    enderecoEntity.setPadrao(true);
                    primeiroEndereco = false;
                } else {
                    enderecoEntity.setPadrao(false);
                }

                enderecoRepository.save(enderecoEntity);
                clienteSalvo.getEnderecos().add(enderecoEntity);
            }
        }

        log.info("Cliente cadastrado com sucesso. ID: {}", clienteSalvo.getId());
        return buscarClientePorId(clienteSalvo.getId());
    }

    @Transactional
    public EnderecoResponseDTO cadastrarNovoEndereco(Long idCliente, EnderecoRequestDTO enderecoRequestDTO){
        log.info("Cadastrando novo endereço para Cliente ID: {}", idCliente);

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new ClienteNaoEncontradoException(idCliente));


        EnderecoResponseDTO enderecoSalvoDTO = enderecoService.cadastrarEndereco(enderecoRequestDTO);

        Endereco enderecoEntity = enderecoRepository.findByIdEndereco(enderecoSalvoDTO.idEndereco())
                .orElseThrow(() -> new EnderecoNaoEncontradoException(enderecoSalvoDTO.idEndereco()));

        enderecoEntity.setCliente(cliente);

        boolean temOutrosEnderecos = enderecoRepository.findByCliente_Id(idCliente).size() > 1;

        if (cliente.getEnderecos() == null || cliente.getEnderecos().isEmpty()) {
            enderecoEntity.setPadrao(true);
        } else {
            enderecoEntity.setPadrao(false);
        }

        enderecoRepository.save(enderecoEntity);

        return new EnderecoResponseDTO(enderecoEntity);
    }

    @Transactional
    public ClienteResponseDTO buscarClientePorId (Long idCliente){
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new ClienteNaoEncontradoException(idCliente));

        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public List<ClienteResponseDTO> buscarClientesAtivos (){
        return clienteRepository.findAllByIsAtivoTrue()
                .stream()
                .map(ClienteResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResumoDTO> listarPedidos(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> {
                    log.warn("Tentativa de listar pedidos. Cliente não encontrado: {}", idCliente); // [AJUSTE] Log de Aviso
                    return new ClienteNaoEncontradoException(idCliente);
                });

        List<PedidoResumoDTO> pedidosCliente = cliente.getPedidos()
                .stream()
                .map(PedidoResumoDTO::new)
                .toList();

        return pedidosCliente;
    }

    @Transactional
    public List<EnderecoResponseDTO> listarEnderecosDoCliente(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new ClienteNaoEncontradoException(idCliente));

        return cliente.getEnderecos().stream().map(EnderecoResponseDTO::new).toList();
    }

    @Transactional
    public ClienteResponseDTO atualizarDadosCliente(Long idCliente, AtualizarClienteRequestDTO requestDTO){
        log.info("A iniciar atualização de dados para cliente ID: {}", idCliente);
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> {
                    log.error("Cliente não encontrado para atualização de dados. ID: {}", idCliente);
                    return new ClienteNaoEncontradoException(idCliente);
                });
        if(requestDTO.nmCliente() != null && !requestDTO.nmCliente().isBlank()){
            cliente.setNmCliente(requestDTO.nmCliente());
        }

        if (requestDTO.emailCliente() != null && !requestDTO.emailCliente().isBlank() &&
                !requestDTO.emailCliente().equalsIgnoreCase(cliente.getEmailCliente())) {

            if (clienteRepository.findByEmailCliente(requestDTO.emailCliente()).isPresent()) {
                log.error("Tentativa de atualizar e-mail para um já cadastrado: {}", requestDTO.emailCliente());
                throw new RegistrosDuplicadosException("E-mail já cadastrado para outro usuário.");
            }

            cliente.setEmailCliente(requestDTO.emailCliente());
        }
        if (requestDTO.telefoneCliente() != null && !requestDTO.telefoneCliente().isBlank()){
            cliente.setTelefoneCliente(requestDTO.telefoneCliente());
        }

        return new ClienteResponseDTO(cliente);
    }

    @Transactional
    public EnderecoResponseDTO atualizarEnderecoCliente(Long idCliente, Long idEndereco, AtualizarEnderecoRequestDTO enderecoRequestDTO) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> {
                    log.error("Cliente não encontrado para atualização de endereço. ID: {}", idCliente); // [AJUSTE] Log de Erro
                    return new ClienteNaoEncontradoException(idCliente);
                });

        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(()-> new EnderecoNaoEncontradoException(idEndereco));

        if (!endereco.getCliente().getId().equals(idCliente)){
            throw new IllegalArgumentException("Endereço não pertence ao cliente informado.");
        }

        if (enderecoRequestDTO.logradouro() != null && !enderecoRequestDTO.logradouro().isBlank()){
            endereco.setLogradouro(enderecoRequestDTO.logradouro());
        }

        if (enderecoRequestDTO.numero() != null && !enderecoRequestDTO.numero().isBlank()){
            endereco.setNumero(enderecoRequestDTO.numero());
        }

        if (enderecoRequestDTO.complemento() != null && !enderecoRequestDTO.complemento().isBlank()){
            endereco.setComplemento(enderecoRequestDTO.complemento());
        }

        if (enderecoRequestDTO.bairro() != null && !enderecoRequestDTO.bairro().isBlank()){
            endereco.setBairro(enderecoRequestDTO.bairro());
        }

        if (enderecoRequestDTO.cidade() != null && !enderecoRequestDTO.cidade().isBlank()){
            endereco.setCidade(enderecoRequestDTO.cidade());
        }

        if (enderecoRequestDTO.cep() != null && !enderecoRequestDTO.cep().isBlank()){
            endereco.setCep(enderecoRequestDTO.cep());
        }

        if (enderecoRequestDTO.estado() != null && !enderecoRequestDTO.estado().isBlank()){
            endereco.setEstado(enderecoRequestDTO.estado());
        }

        if (enderecoRequestDTO.tipoEndereco() != null){
            endereco.setTipoEndereco(enderecoRequestDTO.tipoEndereco());
        }

        enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(endereco);

    }

    @Transactional
    public void deletarCliente(Long idCliente){
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> {
                    log.error("Tentativa de inativar cliente falhou. ID não encontrado: {}", idCliente);
                    return new ClienteNaoEncontradoException(idCliente);
                });

        cliente.setAtivo(false);
    }

    @Transactional
    public boolean iniciarRecuperacaoSenha(String email){
        log.info("Solicitação de recuperação de senha para: {}", email);
        Cliente cliente = clienteRepository.findByEmailCliente(email)
                .orElseThrow(() -> {
                    log.warn("Tentativa de recuperação de senha para e-mail não encontrado: {}", email);
                    return new ClienteNaoEncontradoException(email);
                });

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(15);

        cliente.setTokenRecuperacaoSenha(token);
        cliente.setExpiracaoToken(expiracao);

        clienteRepository.save(cliente);

        String linkRecuperacao = passwordRecoveryUrl + "token=" + token;


            emailService.enviarEmailRecuperacao(cliente.getEmailCliente(), linkRecuperacao)
                    .exceptionally(ex ->{
                        log.error("Falha ao enviar e-mail de recuperação: " + ex.getMessage());
                        return false;
                    });
            return true;

    }

    @Transactional
    public boolean redefinirSenha(String token, String novaSenha){
        Cliente cliente = clienteRepository.findByTokenRecuperacaoSenha(token)
                .orElseThrow(() -> {
                    log.error("Tentativa de redefinição de senha com token inválido ou não encontrado: {}", token);
                    return new IllegalArgumentException("Token não encontrado");
                });

        if (cliente.getExpiracaoToken() != null && cliente.getExpiracaoToken().isBefore(LocalDateTime.now())){
            cliente.setTokenRecuperacaoSenha(null);
            cliente.setExpiracaoToken(null);

            clienteRepository.save(cliente);
            log.warn("Tentativa de redefinição de senha com token expirado: {}", token); // [AJUSTE] Log de Aviso
            throw new RuntimeException("Token de recuperação expirado.");
        }

        cliente.setPassword(passwordEncoder.encode(novaSenha));

        //limpeza de token
        cliente.setTokenRecuperacaoSenha(null);
        cliente.setExpiracaoToken(null);
        clienteRepository.save(cliente);

        log.info("Senha do cliente ID {} redefinida com sucesso.", cliente.getId());
        return true;
    }

    @Transactional
    public EnderecoResponseDTO vincularEnderecoExistente(Long idCliente, Long idEndereco) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        Endereco endereco = enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));

        if (endereco.getCliente() != null || endereco.getRestaurante() != null) {
            throw new RegraDeNegocioException("Este endereço já está vinculado a outro usuário.");
        }

        endereco.setCliente(cliente);

        if (cliente.getEnderecos().isEmpty()) {
            endereco.setPadrao(true);
        } else {
            endereco.setPadrao(false);
        }

        cliente.getEnderecos().add(endereco);
        enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(endereco);
    }

    @Transactional(readOnly = true)
    public List<RestauranteDistanciaDTO> listarRestaurantesProximos(Long idCliente) {

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));

        Endereco enderecoCliente = cliente.getEnderecos().stream()
                .filter(Endereco::isPadrao)
                .findFirst()
                .orElse(cliente.getEnderecos().stream().findFirst().orElse(null));

        if (enderecoCliente == null || enderecoCliente.getLatitude() == null || enderecoCliente.getLongitude() == null) {
            throw new RegraDeNegocioException("O cliente não possui endereço válido (com coordenadas) para calcular distância.");
        }

        List<Restaurante> restaurantes = restauranteRepository.findByIsDisponivelTrue();

        return restaurantes.stream()
                .map(restaurante -> {

                    Endereco origem = restaurante.getEnderecos().stream()
                            .filter(e -> e.getTipoEndereco() == TipoEndereco.MATRIZ)
                            .findFirst()
                            .orElse(restaurante.getEnderecos().stream().findFirst().orElse(null));

                    if (origem != null && origem.getLatitude() != null && origem.getLongitude() != null) {

                        double distancia = DistanciaUtils.calcularDistancia(
                                origem.getLatitude(),
                                origem.getLongitude(),
                                enderecoCliente.getLatitude(),
                                enderecoCliente.getLongitude()
                        );

                        BigDecimal frete = FreteUtils.calcularFrete(distancia);
                        Integer tempo = TempoUtils.calcularTempoEntrega(30, distancia);

                        Double mediaPreco = produtoRepository.calcularMediaPrecoPorRestaurante(restaurante.getId());
                        BigDecimal mediaPrecoBigDecimal = BigDecimal.ZERO;

                        if (mediaPreco != null) {
                            mediaPrecoBigDecimal = BigDecimal.valueOf(mediaPreco).setScale(2, RoundingMode.HALF_UP);
                        }

                        return new RestauranteDistanciaDTO(
                                new RestauranteResponseDTO(restaurante),
                                distancia,
                                frete,
                                tempo,
                                mediaPrecoBigDecimal
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
