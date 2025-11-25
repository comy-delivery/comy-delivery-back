package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.response.EntregadorResponseDTO;
import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.*;
import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;
import com.comy_delivery_back.model.Pedido;
import com.comy_delivery_back.repository.EntregaRepository;
import com.comy_delivery_back.repository.EntregadorRepository;
import com.comy_delivery_back.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class EntregadorService {

    private final EntregadorRepository entregadorRepository;
    private final EntregaRepository entregaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PedidoRepository pedidoRepository;

    public EntregadorService(EntregadorRepository entregadorRepository, EntregaRepository entregaRepository, PasswordEncoder passwordEncoder, EmailService emailService, PedidoRepository pedidoRepository) {
        this.entregadorRepository = entregadorRepository;
        this.entregaRepository = entregaRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public EntregadorResponseDTO cadastrarEntregador(EntregadorRequestDTO entregadorRequestDTO){
        if (entregadorRepository.findByCpfEntregador(entregadorRequestDTO.cpfEntregador()).isPresent()){
            log.error("Tentativa de cadastro com CPF duplicado: {}", entregadorRequestDTO.cpfEntregador());
            throw new RegistrosDuplicadosException("CPF vinculado a uma conta");
        }

        if (entregadorRepository.findByEmailEntregador(entregadorRequestDTO.emailEntregador()).isPresent()){
            log.error("Tentativa de cadastro com EMAIL duplicado: {}", entregadorRequestDTO.emailEntregador());
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
                .orElseThrow(() -> {
                    log.warn("Busca por entregador falhou. ID não encontrado: {}", idEntregador); // [AJUSTE] Log de Aviso
                    return new EntregadorNaoEncontradoException(idEntregador);
                });

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
                .orElseThrow(() -> {
                    log.error("Entregador não encontrado para atualização de dados. ID: {}", idEntregador); // [AJUSTE] Log de Erro
                    return new EntregadorNaoEncontradoException(idEntregador);
                });

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

        if (entrega.getEntregador() == null) {
            throw new RegraDeNegocioException("A entrega não possui um entregador atribuído.");
        }
        Entregador entregador = entrega.getEntregador();
        entregador.setDisponivel(false);

        entrega.setStatusEntrega(StatusEntrega.EM_ROTA);
        entrega.setDataHoraInicio(LocalDateTime.now());
        entregaRepository.save(entrega);
        log.info("Entrega {} iniciada (EM_ROTA).", idEntrega);
    }

    @Transactional
    public void finalizarEntrega(Long idEntrega){

        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> new EntregaNaoEncontradaException(idEntrega));
        Pedido pedido = pedidoRepository.findById((entrega.getPedido().getIdPedido()))
                .orElseThrow(() -> new PedidoNaoEncontradoException(entrega.getPedido().getIdPedido()));

        if (entrega.getStatusEntrega() != StatusEntrega.EM_ROTA) {
            throw new IllegalStateException("A entrega ID " + idEntrega + " não pode ser finalizada. Status atual: " + entrega.getStatusEntrega());
        }

        entrega.setStatusEntrega(StatusEntrega.CONCLUIDA);
        entrega.setDataHoraConclusao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.ENTREGUE);
        entregaRepository.save(entrega);

        Entregador entregador = entrega.getEntregador();
        if (entregador != null) {
            entregador.setDisponivel(true);
            entregadorRepository.save(entregador);
            log.info("Entregador {} liberado (disponível) após finalizar entrega {}.", entregador.getId(), idEntrega);
        }
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

        Entregador entregador = entrega.getEntregador();
        if (entregador != null) {
            entregador.setDisponivel(true);
            entregadorRepository.save(entregador);
            log.info("Entregador {} liberado (disponível) após cancelamento da entrega {}.", entregador.getId(), idEntrega);
        }
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
        log.info("Solicitação de recuperação de senha para entregador: {}", email);
        Entregador entregador = entregadorRepository.findByEmailEntregador(email)
                .orElseThrow(() -> {
                    log.warn("Tentativa de recuperação de senha para e-mail de entregador não encontrado: {}", email);
                    return new EntregadorNaoEncontradoException(email);
                });

        String token = UUID.randomUUID().toString();
        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(15);

        entregador.setTokenRecuperacaoSenha(token);
        entregador.setExpiracaoToken(expiracao);

        entregadorRepository.save(entregador);

        String linkRecuperacao = "http://localhost/8084/reset-password?token=" + token;

        emailService.enviarEmailRecuperacao(entregador.getEmailEntregador(), linkRecuperacao)
                .exceptionally(ex ->{
                    log.error("Falha ao enviar e-mail de recuperação para {}: {}", entregador.getEmailEntregador(), ex.getMessage(), ex);
                    return false;
                });
        return true;
    }

    @Transactional
    public boolean redefinirSenha(String token, String novaSenha){
        Entregador entregador = entregadorRepository.findByTokenRecuperacaoSenha(token)
                .orElseThrow(() -> {
                    log.error("Tentativa de redefinição de senha com token inválido ou não encontrado: {}", token);
                    return new IllegalArgumentException("Token não encontrado");
                });

        if (entregador.getExpiracaoToken() != null && entregador.getExpiracaoToken().isBefore(LocalDateTime.now())){
            // Limpeza de token e lançamento de exceção
            entregador.setTokenRecuperacaoSenha(null);
            entregador.setExpiracaoToken(null);
            entregadorRepository.save(entregador);
            log.warn("Tentativa de redefinição de senha com token expirado: {}", token);
            throw new RuntimeException("Token de recuperação expirado.");
        }

        entregador.setPassword(passwordEncoder.encode(novaSenha));

        // Limpeza de token
        entregador.setTokenRecuperacaoSenha(null);
        entregador.setExpiracaoToken(null);
        entregadorRepository.save(entregador);

        log.info("Senha do entregador ID {} redefinida com sucesso.", entregador.getId());

        return true;
    }
}
