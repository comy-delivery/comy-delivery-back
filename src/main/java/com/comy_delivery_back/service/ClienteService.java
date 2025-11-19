package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AtualizarClienteRequestDTO;
import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ClienteResponseDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.repository.ClienteRepository;
import com.comy_delivery_back.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        if (clienteRepository.findByCpfCliente(clienteRequestDTO.cpfCliente()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        if (clienteRepository.findByEmailCliente(clienteRequestDTO.emailCliente()).isPresent()) {
            throw new IllegalArgumentException("EMAIL já cadastrado!");
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

        clienteRepository.save(novoCliente);

        return new ClienteResponseDTO(novoCliente);
    }

    @Transactional
    public EnderecoResponseDTO cadastrarNovoEndereco(Long idCliente, EnderecoRequestDTO enderecoRequestDTO){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new IllegalArgumentException("Cliente não encontrado"));

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
                .orElseThrow(()-> new IllegalArgumentException("Id informado não corresponde a nenhum cliente"));

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
                .orElseThrow(() -> new IllegalArgumentException("id cliente não encontrado."));

        List<PedidoResponseDTO> pedidosCliente = cliente.getPedidos()
                .stream()
                .map(PedidoResponseDTO::new)
                .toList();

        return pedidosCliente;
    }

    @Transactional
    public ClienteResponseDTO atualizarDadosCliente(Long idCliente, AtualizarClienteRequestDTO requestDTO){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new IllegalArgumentException("Cliente não encontrado."));

        if(requestDTO.nmCliente() != null && !requestDTO.nmCliente().isBlank()){
            cliente.setNmCliente(requestDTO.nmCliente());
        }

        if (requestDTO.emailCliente() != null && !requestDTO.emailCliente().isBlank() &&
                !requestDTO.emailCliente().equalsIgnoreCase(cliente.getEmailCliente())) {

            if (clienteRepository.findByEmailCliente(requestDTO.emailCliente()).isPresent()) {
                throw new IllegalArgumentException("E-mail já cadastrado para outro usuário.");
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
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

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
                .orElseThrow(()-> new IllegalArgumentException("Usuario não encontrado pelo ID fornecido."));

        cliente.setAtivo(false);
    }

    @Transactional
    public boolean iniciarRecuperacaoSenha(String email){
        Cliente cliente = clienteRepository.findByEmailCliente(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado para o email: " + email));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(15);

        cliente.setTokenRecuperacaoSenha(token);
        cliente.setExpiracaoToken(expiracao);

        clienteRepository.save(cliente);

        String linkRecuperacao = "http://localhost/8084/reset-password?token=" + token;

        try{
            emailService.enviarEmailRecuperacao(cliente.getEmailCliente(), linkRecuperacao);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar e-mail de recuperação ", e);
        }
    }

    @Transactional
    public boolean redefinirSenha(String token, String novaSenha){
        Cliente cliente = clienteRepository.findByTokenRecuperacao(token)
                .orElseThrow(() -> new IllegalArgumentException("Token não encontrado"));

        if (cliente.getExpiracaoToken() != null && cliente.getExpiracaoToken().isBefore(LocalDateTime.now())){
            cliente.setTokenRecuperacaoSenha(null);
            cliente.setExpiracaoToken(null);

            clienteRepository.save(cliente);
            throw new RuntimeException("Token de recuperação expirado.");
        }

        cliente.setPassword(passwordEncoder.encode(novaSenha));

        //limpeza de token
        cliente.setTokenRecuperacaoSenha(null);
        cliente.setExpiracaoToken(null);
        clienteRepository.save(cliente);

        return true;
    }
}
