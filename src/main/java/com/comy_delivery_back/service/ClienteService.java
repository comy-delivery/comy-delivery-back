package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AtualizarClienteRequestDTO;
import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ClienteResponseDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.model.Cliente;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.model.Usuario;
import com.comy_delivery_back.repository.ClienteRepository;
import com.comy_delivery_back.repository.EnderecoRepository;
import com.comy_delivery_back.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @Autowired
    private EnderecoRepository enderecoRepository;

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
        novoCliente.setPassword(clienteRequestDTO.password()); //encoder aqui depois
        novoCliente.setNmCliente(clienteRequestDTO.nmCliente());
        novoCliente.setCpfCliente(clienteRequestDTO.cpfCliente());
        novoCliente.setEmailCliente(clienteRequestDTO.emailCliente());
        novoCliente.setTelefoneCliente(clienteRequestDTO.telefoneCliente());
        //colocar role mesmo já sendo setado no model?

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

        return new ClienteResponseDTO(
                novoCliente.getId(),
                novoCliente.getUsername(),
                novoCliente.getNmCliente(),
                novoCliente.getEmailCliente(),
                novoCliente.getCpfCliente(),
                novoCliente.getTelefoneCliente(),
                novoCliente.getDataCadastroCliente(),//ver se ta retornando mesmo sem setar no service
                novoCliente.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                novoCliente.isAtivo()
        );
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

        List<EnderecoResponseDTO> enderecoResponseDTOS = cliente.getEnderecos()
                .stream()
                .map(EnderecoResponseDTO::new)
                .toList();

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getNmCliente(),
                cliente.getEmailCliente(),
                cliente.getCpfCliente(),
                cliente.getTelefoneCliente(),
                cliente.getDataCadastroCliente(),
                enderecoResponseDTOS,
                cliente.isAtivo()
        );
    }

    @Transactional
    public List<ClienteResponseDTO> buscarClientesAtivos (){
        return clienteRepository.findAllByIsAtivoTrue()
                .stream()
                .map(c -> {
                    List<EnderecoResponseDTO> enderecoResponseDTOS = c.getEnderecos().stream()
                            .map(EnderecoResponseDTO::new)
                            .toList();

                    return new ClienteResponseDTO(
                            c.getId(),
                            c.getUsername(),
                            c.getNmCliente(),
                            c.getEmailCliente(),
                            c.getCpfCliente(),
                            c.getTelefoneCliente(),
                            c.getDataCadastroCliente(),
                            enderecoResponseDTOS,
                            c.isAtivo()
                    );
                }).toList();
    }

    public List<PedidoResponseDTO> listarPedidos(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("id cliente não encontrado."));

        List<PedidoResponseDTO> pedidosCliente = cliente.getPedidos()
                .stream()
                .map(PedidoResponseDTO::new)
                .toList();

        return pedidosCliente;
    }


    public ClienteResponseDTO atualizarDadosCliente(Long idCliente, AtualizarClienteRequestDTO requestDTO){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new IllegalArgumentException("Cliente não encontrado."));

        if(requestDTO.nmCliente() != null && !requestDTO.nmCliente().isBlank()){
            cliente.setNmCliente(requestDTO.nmCliente());
        }

        if(requestDTO.emailCliente() != null && !requestDTO.emailCliente().isBlank()){
            if (requestDTO.emailCliente().equals(cliente.getEmailCliente())){
                throw new IllegalArgumentException("Email pertence a outro usuario");
            }

            cliente.setEmailCliente(requestDTO.emailCliente());
        }
        if (requestDTO.telefoneCliente() != null && !requestDTO.telefoneCliente().isBlank()){
            cliente.setTelefoneCliente(requestDTO.telefoneCliente());
        }

        List<EnderecoResponseDTO> enderecoResponseDTOS = cliente.getEnderecos()
                .stream()
                .map(EnderecoResponseDTO::new)
                .toList();

        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getNmCliente(),
                cliente.getEmailCliente(),
                cliente.getCpfCliente(),
                cliente.getTelefoneCliente(),
                cliente.getDataCadastroCliente(),
                enderecoResponseDTOS,
                cliente.isAtivo()

        );
    }

    /*
    public EnderecoResponseDTO atualizarEnderecoCliente(Long idCliente){
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(()-> new IllegalArgumentException("Cliente não encontrado."));

    }*/

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

        String linkRecuperacao = "http://localhost/8084/reset-password?token=" + token;

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
