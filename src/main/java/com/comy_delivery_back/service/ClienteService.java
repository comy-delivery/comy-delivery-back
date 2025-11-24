package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AtualizarClienteRequestDTO;
import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ClienteResponseDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.exception.ClienteNaoEncontradoException;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.exception.RegistrosDuplicadosException;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.repository.ClienteRepository;
import com.comy_delivery_back.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;//usar depois
    private final EmailService emailService;

    public ClienteService(ClienteRepository clienteRepository,
                          EnderecoRepository enderecoRepository, PasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

        List<Endereco> enderecos = clienteRequestDTO.enderecos().stream()
                        .map(enderecoRequestDTO -> {
                            Endereco endereco = new Endereco();

                            endereco.setLogradouro(enderecoRequestDTO.logradouro());
                            endereco.setNumero(enderecoRequestDTO.numero());
                            endereco.setComplemento(enderecoRequestDTO.complemento());
                            endereco.setBairro(enderecoRequestDTO.bairro());
                            endereco.setCidade(enderecoRequestDTO.cidade());
                            endereco.setCep(enderecoRequestDTO.cep());
                            endereco.setEstado(enderecoRequestDTO.estado());
                            endereco.setTipoEndereco(enderecoRequestDTO.tipoEndereco());

                            return endereco;
                        }).toList();
        novoCliente.setEnderecos(enderecos); //cliente recebe o endereco

        enderecos.forEach(endereco -> endereco.setCliente(novoCliente)); //endereco recebe o cliente que pertence

        Cliente clienteSalvo = clienteRepository.save(novoCliente);
        log.info("Cliente cadastrado com sucesso. ID: {}", clienteSalvo.getId());
        return new ClienteResponseDTO(clienteSalvo);
    }

    @Transactional
    public EnderecoResponseDTO cadastrarNovoEndereco(Long idCliente, EnderecoRequestDTO enderecoRequestDTO){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new ClienteNaoEncontradoException(idCliente));

        Endereco novoEndereco = new Endereco();

        novoEndereco.setLogradouro(enderecoRequestDTO.logradouro());
        novoEndereco.setNumero(enderecoRequestDTO.numero());
        novoEndereco.setComplemento(enderecoRequestDTO.complemento());
        novoEndereco.setBairro(enderecoRequestDTO.bairro());
        novoEndereco.setCidade(enderecoRequestDTO.cidade());
        novoEndereco.setEstado(enderecoRequestDTO.estado());
        novoEndereco.setTipoEndereco(enderecoRequestDTO.tipoEndereco());
        novoEndereco.setCliente(cliente);

        enderecoRepository.save(novoEndereco);

        return new EnderecoResponseDTO(novoEndereco);
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

    @Transactional
    public List<PedidoResponseDTO> listarPedidos(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> {
                    log.warn("Tentativa de listar pedidos. Cliente não encontrado: {}", idCliente); // [AJUSTE] Log de Aviso
                    return new ClienteNaoEncontradoException(idCliente);
                });

        List<PedidoResponseDTO> pedidosCliente = cliente.getPedidos()
                .stream()
                .map(PedidoResponseDTO::new)
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
    public EnderecoResponseDTO atualizarEnderecoCliente(Long idCliente, Long idEndereco, EnderecoRequestDTO enderecoRequestDTO) {
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

        String linkRecuperacao = "http://localhost/8084/reset-password?token=" + token;


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
}
