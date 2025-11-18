package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.ProdutoRequestDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.exception.ProdutoNaoEncontradoException;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.ProdutoRepository;
import com.comy_delivery_back.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;

    public ProdutoService(ProdutoRepository produtoRepository, RestauranteRepository restauranteRepository) {
        this.produtoRepository = produtoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto, MultipartFile imagemFile) throws IOException {
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Produto produto = new Produto();
        BeanUtils.copyProperties(dto, produto);

        if (imagemFile != null && !imagemFile.isEmpty()) {
            produto.setImagemProduto(imagemFile.getBytes());
        } else {
            produto.setImagemProduto(null);
        }

        produto.setDsProduto(dto.dsProduto());
        produto.setRestaurante(restaurante);
        produto.setTempoPreparacao(dto.tempoPreparacao());
        produto.setVlPrecoPromocional(dto.vlPrecoPromocional());

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        return this.produtoRepository.findById(id).map(ProdutoResponseDTO::new)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestaurante_IdRestauranteAndIsAtivoTrue(restauranteId).stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarPromocoes() {
        return produtoRepository.findByIsPromocaoTrue().stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto, MultipartFile imagemFile) throws IOException {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        BeanUtils.copyProperties(dto, produto);

        if (imagemFile != null && !imagemFile.isEmpty()) {
            produto.setImagemProduto(imagemFile.getBytes());
        }
        produto.setDsProduto(dto.dsProduto());
        produto.setRestaurante(restaurante);
        produto.setTempoPreparacao(dto.tempoPreparacao());
        produto.setVlPrecoPromocional(dto.vlPrecoPromocional());

        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

}
