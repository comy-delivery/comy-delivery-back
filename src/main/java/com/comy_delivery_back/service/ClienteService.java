package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.response.ClienteResponseDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.repository.ClienteRepository;
import com.comy_delivery_back.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRepository.findByCpfCliente(clienteRequestDTO.cpfCliente()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }

        if (clienteRepository.findByCpfCliente(clienteRequestDTO.emailCliente()).isPresent()) {
            throw new IllegalArgumentException("EMAIL já cadastrado!");
        }

        Cliente novoCliente = new Cliente();


        novoCliente.setUsername(clienteRequestDTO.username());
        novoCliente.setPassword(clienteRequestDTO.password()); //encoder aqui depois
        novoCliente.setNmCliente(clienteRequestDTO.nmCliente());
        novoCliente.setCpfCliente(clienteRequestDTO.cpfCliente());
        novoCliente.setEmailCliente(clienteRequestDTO.emailCliente());
        novoCliente.setTelefoneCliente(clienteRequestDTO.telefoneCliente());
        //colocar role mesmo já sendo setado no model?

        //olhar depois
        Endereco endereco = new Endereco();
        novoCliente.setEnderecos((List<Endereco>) endereco);

        clienteRepository.save(novoCliente);

        return new ClienteResponseDTO(
                novoCliente.getId(),
                novoCliente.getUsername(),
                novoCliente.getNmCliente(),
                novoCliente.getEmailCliente(),
                novoCliente.getCpfCliente(),
                novoCliente.getTelefoneCliente(),
                novoCliente.getDataCadastroCliente(), //ver se ta retornando mesmo sem setar no service
                novoCliente.isAtivo()
        );
    }

    @Transactional
    public ClienteResponseDTO buscarClientePorId (Long idCliente){
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new IllegalArgumentException("Id informado não corresponde a nenhum cliente"));

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getNmCliente(),
                cliente.getEmailCliente(),
                cliente.getCpfCliente(),
                cliente.getTelefoneCliente(),
                cliente.getDataCadastroCliente(),
                cliente.isAtivo()
        );
    }

    @Transactional
    public List<ClienteResponseDTO> buscarClientesAtivos (){
        return clienteRepository.findAllByIsAtivoTrue()
                .stream()
                .map(c -> new ClienteResponseDTO(
                        c.getId(),
                        c.getUsername(),
                        c.getNmCliente(),
                        c.getEmailCliente(),
                        c.getCpfCliente(),
                        c.getTelefoneCliente(),
                        c.getDataCadastroCliente(),
                        c.isAtivo()
                )).toList();
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
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado para o email: " + email));

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(15);

        cliente.setTokenRecuperacaoSenha(token);
        cliente.setExpiracaoToken(expiracao);

        clienteRepository.save(cliente);

        String linkRecuperacao = "URL_DO_SEU_FRONTEND/reset-password?token=" + token;

        try{
            emailService.enviarEmailRecuperacao(cliente.getEmailCliente(), linkRecuperacao);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar e-mail de recuperação ", e);
        }
    }

    public boolean redefinirSenha(String token, String novaSenha){
        Cliente cliente = clienteRepository.findByTokenRecuperacao(token)
                .orElseThrow(() -> new IllegalArgumentException("Token não encontrado"));

        if (cliente.getExpiracaoToken() != null && cliente.getExpiracaoToken().isBefore(LocalDateTime.now())){
            cliente.setTokenRecuperacaoSenha(null);
            cliente.setExpiracaoToken(null);

            clienteRepository.save(cliente);
            throw new RuntimeException("Token de recuperação expirado.");
        }

        cliente.setPassword(novaSenha); //criptografar depois

        //limpeza de token
        cliente.setTokenRecuperacaoSenha(null);
        cliente.setExpiracaoToken(null);
        clienteRepository.save(cliente);

        return true;
    }



}
