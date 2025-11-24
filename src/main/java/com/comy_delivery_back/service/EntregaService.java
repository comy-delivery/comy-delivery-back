package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AtualizarStatusEntregaDTO;
import com.comy_delivery_back.dto.request.EntregaRequestDTO;
import com.comy_delivery_back.dto.response.EntregaResponseDTO;
import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.exception.EntregaNaoEncontradaException;
import com.comy_delivery_back.exception.EntregadorNaoEncontradoException;
import com.comy_delivery_back.exception.PedidoNaoEncontradoException;
import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;
import com.comy_delivery_back.model.Pedido;
import com.comy_delivery_back.repository.EntregaRepository;
import com.comy_delivery_back.repository.EntregadorRepository;
import com.comy_delivery_back.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class EntregaService {
    private final EntregaRepository entregaRepository;
    private final PedidoRepository pedidoRepository;
    private final EntregadorRepository entregadorRepository;

    public EntregaService(EntregaRepository entregaRepository, PedidoRepository pedidoRepository, EntregadorRepository entregadorRepository) {
        this.entregaRepository = entregaRepository;
        this.pedidoRepository = pedidoRepository;
        this.entregadorRepository = entregadorRepository;
    }

    @Transactional
    public EntregaResponseDTO cadastrarEntrega(EntregaRequestDTO entregaRequestDTO){

        log.info("A criar nova entrega para o pedido ID: {}", entregaRequestDTO.pedidoId());

        Pedido pedido = pedidoRepository.findById(entregaRequestDTO.pedidoId())
                .orElseThrow(() -> new PedidoNaoEncontradoException(entregaRequestDTO.pedidoId()));

        Entrega novaEntrega = new Entrega();

        novaEntrega.setPedido(pedido);
        novaEntrega.setTempoEstimadoMinutos(entregaRequestDTO.tempoEstimadoMinutos() != null ? entregaRequestDTO.tempoEstimadoMinutos() : 30);
        novaEntrega.setStatusEntrega(StatusEntrega.PENDENTE);
        novaEntrega.setEnderecoOrigem(pedido.getEnderecoOrigem());
        novaEntrega.setEnderecoDestino(pedido.getEnderecoEntrega());

        Entrega entrega = entregaRepository.save(novaEntrega);

        log.info("Entrega criada com sucesso. ID Entrega: {}", entrega.getIdEntrega());
        return new EntregaResponseDTO(entrega);

    }

    @Transactional
    public EntregaResponseDTO atualizarEntrega(Long idEntrega, AtualizarStatusEntregaDTO atualizarStatusEntregaDTO){
        log.info("A atualizar entrega ID: {}. Novo Status: {}", idEntrega, atualizarStatusEntregaDTO.statusEntrega());

        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(() -> {
                    log.error("Entrega não encontrada: {}", idEntrega);
                    return new EntregaNaoEncontradaException(idEntrega);
                });

        StatusEntrega novoStatus = atualizarStatusEntregaDTO.statusEntrega();
        StatusEntrega statusAtual = entrega.getStatusEntrega();

        if (novoStatus == StatusEntrega.EM_ROTA){
            if (statusAtual != StatusEntrega.PENDENTE){
                throw new IllegalArgumentException("Entrega só pode ser iniciada se estiver PENDENTE. Status atual: " + statusAtual);
            }

            if (atualizarStatusEntregaDTO.entregadorId() == null){
                throw new IllegalArgumentException("É obrigatório informar o ID do entregador para iniciar a entrega (EM_ROTA).");
            }

            entrega.setDataHoraInicio(LocalDateTime.now());

            log.info("Entrega {} iniciada pelo entregador {}", idEntrega, atualizarStatusEntregaDTO.entregadorId());
            Entregador entregador = entregadorRepository.findById(atualizarStatusEntregaDTO.entregadorId())
                    .orElseThrow(() -> new EntregadorNaoEncontradoException(atualizarStatusEntregaDTO.entregadorId()));

            entrega.setEntregador(entregador);

        } else if (novoStatus == StatusEntrega.CONCLUIDA) {
            if (statusAtual != StatusEntrega.EM_ROTA){
                throw new IllegalArgumentException("Entrega só pode ser concluída se estiver EM ROTA. Status atual: " + statusAtual);
            }

            entrega.setDataHoraConclusao(LocalDateTime.now());
            if (atualizarStatusEntregaDTO.avaliacaoCliente() != null) {
                if (atualizarStatusEntregaDTO.avaliacaoCliente() < 0 || atualizarStatusEntregaDTO.avaliacaoCliente() > 5) {
                    throw new IllegalArgumentException("A avaliação deve estar entre 0 e 5.");
                }
                log.info("Entrega {} concluída. Avaliação: {}", idEntrega, atualizarStatusEntregaDTO.avaliacaoCliente());
                entrega.setAvaliacaoCliente(atualizarStatusEntregaDTO.avaliacaoCliente());
            } else {
                throw new IllegalArgumentException("A avaliação do cliente é obrigatória na conclusão da entrega.");
            }

        } else if (novoStatus == StatusEntrega.CANCELADA) {
            if (statusAtual != StatusEntrega.PENDENTE) {
                throw new IllegalArgumentException("Entrega só pode ser cancelada se estiver PENDENTE. Status atual: " + statusAtual);
            }
            log.warn("Entrega {} CANCELADA.", idEntrega);

        } else if (novoStatus == StatusEntrega.PENDENTE) {
            throw new IllegalArgumentException("Não é permitido transicionar manualmente para o status PENDENTE.");

        }

        entrega.setStatusEntrega(novoStatus);

        Entrega entregaAtualizada = entregaRepository.save(entrega);

        return new EntregaResponseDTO(entregaAtualizada);
    }

    @Transactional
    public EntregaResponseDTO buscarEntregaPorId(Long idEntrega){
        Entrega entrega = entregaRepository.findById(idEntrega)
                .orElseThrow(()-> new EntregaNaoEncontradaException(idEntrega));

        return new EntregaResponseDTO(entrega);
    }

    @Transactional
    public EntregaResponseDTO buscarEntregaPorIdPedido(Long idPedido){
        Entrega entrega = entregaRepository.findByPedido_IdPedido(idPedido)
                .orElseThrow(()-> new PedidoNaoEncontradoException(idPedido));

        return new EntregaResponseDTO(entrega);
    }

    @Transactional
    public List<EntregaResponseDTO> buscarEntregaEntregadorPorStatus(Long idEntregador, StatusEntrega statusEntrega){
        return entregaRepository.findByEntregadorIdAndStatusEntrega(idEntregador, statusEntrega)
                .stream()
                .map(EntregaResponseDTO::new).toList();

    }

    @Transactional
    public List<EntregaResponseDTO> buscarEntregaPorStatus(StatusEntrega statusEntrega){
        return entregaRepository.findByStatusEntrega(statusEntrega)
                .stream()
                .map(EntregaResponseDTO::new).toList();
    }

    @Transactional
    public List<EntregaResponseDTO> buscarEntregasPorEntregador(Long idEntregador){
        return entregaRepository.findByEntregadorId(idEntregador)
                .stream()
                .map(EntregaResponseDTO::new).toList();
    }

}