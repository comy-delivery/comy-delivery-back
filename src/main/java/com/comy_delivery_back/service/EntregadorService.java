package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.response.EntregadorResponseDTO;
import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.exception.EntregaNaoEncontradaException;
import com.comy_delivery_back.exception.EntregadorNaoEncontradoException;
import com.comy_delivery_back.exception.RegistrosDuplicadosException;
import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;
import com.comy_delivery_back.repository.EntregaRepository;
import com.comy_delivery_back.repository.EntregadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EntregadorService {

    private final EntregadorRepository entregadorRepository;
    private final EntregaRepository entregaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public EntregadorService(EntregadorRepository entregadorRepository, EntregaRepository entregaRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.entregadorRepository = entregadorRepository;
        this.entregaRepository = entregaRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public EntregadorResponseDTO cadastrarEntregador(EntregadorRequestDTO entregadorRequestDTO){
        if (entregadorRepository.findByCpfEntregador(entregadorRequestDTO.cpfEntregador()).isPresent()){
            throw new RegistrosDuplicadosException("CPF vinculado a uma conta");
        }

        if (entregadorRepository.findByEmailEntregador(entregadorRequestDTO.emailEntregador()).isPresent()){
            throw new RegistrosDuplicadosException("Email vinculado a uma conta");
        }

        Entregador novoEntregador = new Entregador();

        novoEntregador.setUsername(entregadorRequestDTO.username());
        novoEntregador.setPassword(passwordEncoder.encode(entregadorRequestDTO.password()));
        novoEntregador.setNmEntregador(entregadorRequestDTO.nmEntregador());
        novoEntregador.setEmailEntregador(entregadorRequestDTO.emailEntregador());
        novoEntregador.setCpfEntregador(entregadorRequestDTO.cpfEntregador());
        novoEntregador.setTelefoneEntregador(entregadorRequestDTO.telefoneEntregador());
        novoEntregador.setVeiculo(entregadorRequestDTO.veiculo());
        novoEntregador.setPlaca(entregadorRequestDTO.placa());

        entregadorRepository.save(novoEntregador);
        return new EntregadorResponseDTO(novoEntregador);
    }

    @Transactional
    public EntregadorResponseDTO buscarEntregadorPorId(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(() -> new EntregadorNaoEncontradoException(idEntregador));

        return new EntregadorResponseDTO(entregador);
    }

    public List<EntregadorResponseDTO> listarEntregadoresDisponiveis(){
        List<EntregadorResponseDTO> entregadoresDisponiveis = entregadorRepository.findByIsDisponivelTrue()
                .stream()
                .map(EntregadorResponseDTO::new).toList();

        return entregadoresDisponiveis;
    }

    public EntregadorResponseDTO atualizarDadosEntregador(Long idEntregador, EntregadorRequestDTO entregadorRequestDTO){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(() -> new EntregadorNaoEncontradoException(idEntregador));

        if (entregadorRequestDTO.nmEntregador() != null && !entregadorRequestDTO.nmEntregador().isBlank()){
            entregador.setNmEntregador(entregadorRequestDTO.nmEntregador());
        }

        if (entregadorRequestDTO.emailEntregador() != null && !entregadorRequestDTO.emailEntregador().isBlank()){
            entregador.setEmailEntregador(entregadorRequestDTO.emailEntregador());
        }

        if(entregadorRequestDTO.telefoneEntregador() != null && !entregadorRequestDTO.telefoneEntregador().isBlank()){
            entregador.setTelefoneEntregador(entregadorRequestDTO.telefoneEntregador());
        }

        if (entregadorRequestDTO.veiculo() != null){
            entregador.setVeiculo(entregadorRequestDTO.veiculo());
        }

        if (entregadorRequestDTO.placa() != null && !entregadorRequestDTO.placa().isBlank()){
            entregador.setPlaca(entregadorRequestDTO.placa());
        }

        entregadorRepository.save(entregador);

        return new EntregadorResponseDTO(entregador);
    }

    @Transactional
    public void atribuirEntrega(Long idEntregador, Long idEntrega) {
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(() -> new EntregadorNaoEncontradoException(idEntregador));

        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new EntregaNaoEncontradaException(idEntrega));

        if (!entregador.isDisponivel()) {
            throw new IllegalStateException("Entregador ID " + idEntregador + " não está disponível para receber novas entregas.");
        }

        if (entrega.getStatusEntrega() != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("Entrega ID " + idEntrega + " já está em processo ou concluída.");
        }

        entrega.setEntregador(entregador); //associa o entregador a entrega

        //entregador precisa inciar entrega
        entregaRepository.save(entrega);
    }

    @Transactional
    public void marcarComoDisponivel(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(()-> new EntregaNaoEncontradaException(idEntregador));

        entregador.setDisponivel(true);
    }

    @Transactional
    public void marcarComoIndisponivel(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(()-> new EntregaNaoEncontradaException(idEntregador));

        entregador.setDisponivel(false);
    }

    @Transactional
    public void iniciarEntrega(Long idEntrega){

        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new EntregaNaoEncontradaException(idEntrega));

        if (entrega.getStatusEntrega() != StatusEntrega.PENDENTE) {
            throw new IllegalStateException("A entrega ID " + idEntrega + " não pode ser iniciada. Status atual: " + entrega.getStatusEntrega());
        }

        entrega.setStatusEntrega(StatusEntrega.EM_ROTA);

        entregaRepository.save(entrega);
    }

    @Transactional
    public void finalizarEntrega(Long idEntrega){

        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new EntregaNaoEncontradaException(idEntrega));

        if (entrega.getStatusEntrega() != StatusEntrega.EM_ROTA) {
            throw new IllegalStateException("A entrega ID " + idEntrega + " não pode ser finalizada. Status atual: " + entrega.getStatusEntrega());
        }

        entrega.setStatusEntrega(StatusEntrega.CONCLUIDA);

        entregaRepository.save(entrega);
    }

    @Transactional
    public void cancelarEntrega(Long idEntrega) {
        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new EntregaNaoEncontradaException(idEntrega));

        if (entrega.getStatusEntrega() == StatusEntrega.CONCLUIDA || entrega.getStatusEntrega() == StatusEntrega.CANCELADA) {
            throw new IllegalStateException("A entrega ID " + idEntrega + " não pode ser cancelada. Status atual: " + entrega.getStatusEntrega());
        }

        entrega.setStatusEntrega(StatusEntrega.CANCELADA);

        entregaRepository.save(entrega);
    }

    @Transactional
    public void deletarEntregador(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(()-> new EntregadorNaoEncontradoException(idEntregador));

        entregador.setAtivo(false);
        entregador.setDisponivel(false);

        entregadorRepository.save(entregador);
    }

    @Transactional
    public boolean iniciarRecuperacaoSenha(String email){
        Entregador entregador = entregadorRepository.findByEmailEntregador(email)
                .orElseThrow(() -> new EntregadorNaoEncontradoException(email)); // Use sua exceção customizada

        //gera o token e expiracao
        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(15);

        entregador.setTokenRecuperacaoSenha(token);
        entregador.setExpiracaoToken(expiracao);

        entregadorRepository.save(entregador);

        //monta o link de recuperacao
        String linkRecuperacao = "http://localhost:8084/reset-password?token=" + token; // Ajuste a porta/rota conforme necessário

        //envia o email
        emailService.enviarEmailRecuperacao(entregador.getEmailEntregador(), linkRecuperacao)
                .exceptionally(ex ->{
                    //log.error("Falha ao enviar e-mail de recuperação: " + ex.getMessage());
                    return false;
                });
        return true;
    }

    @Transactional
    public boolean redefinirSenha(String token, String novaSenha){
        // busca o entregador pelo token
        Entregador entregador = entregadorRepository.findByTokenRecuperacaoSenha(token)
                .orElseThrow(() -> new IllegalArgumentException("Token de recuperação inválido ou não encontrado."));

        //verifica se o token expirou
        if (entregador.getExpiracaoToken() != null && entregador.getExpiracaoToken().isBefore(LocalDateTime.now())){

            //Limpa o token expirado
            entregador.setTokenRecuperacaoSenha(null);
            entregador.setExpiracaoToken(null);
            entregadorRepository.save(entregador);

            throw new RuntimeException("Token de recuperação expirado. Por favor, solicite a recuperação novamente.");
        }

        //codifica e define a nova senha
        entregador.setPassword(passwordEncoder.encode(novaSenha));

        //limpa o token depois do uso
        entregador.setTokenRecuperacaoSenha(null);
        entregador.setExpiracaoToken(null);
        entregadorRepository.save(entregador);

        return true;
    }
}
