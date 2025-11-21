package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AvaliacaoRequestDTO;
import com.comy_delivery_back.dto.response.AvaliacaoResponseDTO;
import com.comy_delivery_back.exception.PedidoNaoEncontradoException;
import com.comy_delivery_back.model.*;
import com.comy_delivery_back.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final EntregadorRepository entregadorRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, RestauranteRepository restauranteRepository, ClienteRepository clienteRepository, PedidoRepository pedidoRepository, EntregadorRepository entregadorRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restauranteRepository = restauranteRepository;
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.entregadorRepository = entregadorRepository;
    }

    @Transactional
    public AvaliacaoResponseDTO adicionarAvaliacao(AvaliacaoRequestDTO dto) {
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new PedidoNaoEncontradoException(dto.pedidoId()));

        Entregador entregador = entregadorRepository.findById(dto.entregadorId())
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));

        Avaliacao avaliacao = new Avaliacao();

        BeanUtils.copyProperties(dto, avaliacao);

        avaliacao.setRestaurante(restaurante);
        avaliacao.setCliente(cliente);
        avaliacao.setPedido(pedido);
        avaliacao.setEntregador(entregador);

        avaliacao.setDsComentario(dto.dsComentario());


        if (dto.avaliacaoEntrega() == null) {
            avaliacao.setAvaliacaoEntrega(dto.nuNota());
        } else {
            avaliacao.setAvaliacaoEntrega(dto.avaliacaoEntrega());
        }

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        atualizarMediaRestaurante(dto.restauranteId());
        atualizarMediaEntregador(dto.entregadorId());

        return new AvaliacaoResponseDTO(avaliacaoSalva);
    }

    public List<AvaliacaoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return avaliacaoRepository.findByRestaurante_IdRestaurante(restauranteId).stream()
                .map(AvaliacaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void atualizarMediaRestaurante(Long restauranteId) {
        Double media = avaliacaoRepository.calcularMediaAvaliacaoRestaurante(restauranteId);
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));
        restaurante.setAvaliacaoMediaRestaurante(media != null ? media : 0.0);
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void atualizarMediaEntregador(Long entregadorId) {
        Double media = avaliacaoRepository.calcularMediaAvaliacaoEntregador(entregadorId);
        Entregador entregador = entregadorRepository.findById(entregadorId)
                .orElseThrow(() -> new RuntimeException("Entregador não encontrado"));
        entregador.setAvaliacaoMediaEntregador(media != null ? media : 0.0);
        entregadorRepository.save(entregador);
    }

    @Transactional
    public void deletarAvaliacao(Long id) {
        avaliacaoRepository.deleteById(id);
    }

}
