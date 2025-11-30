package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.ProdutoRequestDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.exception.ProdutoNaoEncontradoException;
import com.comy_delivery_back.exception.RegraDeNegocioException;
import com.comy_delivery_back.exception.RestauranteNaoEncontradoException;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.ProdutoRepository;
import com.comy_delivery_back.repository.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("A criar produto '{}' para o restaurante ID: {}", dto.nmProduto(), dto.restauranteId());
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new RestauranteNaoEncontradoException(dto.restauranteId()));

        Produto produto = new Produto();
        BeanUtils.copyProperties(dto, produto);

        if (imagemFile != null && !imagemFile.isEmpty()) {
            byte[] imagemBytes = imagemFile.getBytes();
            produto.setImagemProduto(imagemBytes);
        } else {
            produto.setImagemProduto(null);
        }

        produto.setDsProduto(dto.dsProduto());
        produto.setRestaurante(restaurante);
        produto.setTempoPreparacao(dto.tempoPreparacao());
        produto.setVlPrecoPromocional(dto.vlPrecoPromocional());

        return new ProdutoResponseDTO(produtoRepository.save(produto));
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        return this.produtoRepository.findById(id).map(ProdutoResponseDTO::new)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestaurante_IdAndIsAtivoTrue(restauranteId).stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
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
                .orElseThrow(() -> new RestauranteNaoEncontradoException(dto.restauranteId()));

        BeanUtils.copyProperties(dto, produto);

        if (imagemFile != null && !imagemFile.isEmpty()) {
            byte[] imagemBytes = imagemFile.getBytes();
            produto.setImagemProduto(imagemBytes);
        } else {
            produto.setImagemProduto(null);
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
        log.info("A inativar (soft delete) produto ID: {}", id);
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
        produto.setAtivo(false);

        produtoRepository.delete(produto);
    }

    @Transactional
    public void atualizarImagemProduto(Long idProduto, MultipartFile imagem) throws IOException {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(idProduto));

        if (imagem != null && !imagem.isEmpty()) {
            produto.setImagemProduto(imagem.getBytes());
            produtoRepository.save(produto);
        }
    }

    @Transactional(readOnly = true)
    public byte[] buscarImagemProduto(Long idProduto) {
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(idProduto));
        return produto.getImagemProduto();
    }

    @Transactional
    public ProdutoResponseDTO atualizarPromocao(Long id, BigDecimal vlPrecoPromocional, Boolean isPromocao) {
        log.info("Atualizando promoção do produto ID: {}", id);

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        if (isPromocao != null && isPromocao) {
            if (vlPrecoPromocional == null || vlPrecoPromocional.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RegraDeNegocioException("Preço promocional deve ser maior que zero");
            }

            if (vlPrecoPromocional.compareTo(produto.getVlPreco()) >= 0) {
                throw new RegraDeNegocioException("Preço promocional deve ser menor que o preço normal");
            }

            produto.setIsPromocao(true);
            produto.setVlPrecoPromocional(vlPrecoPromocional);
            log.info("Produto ID {} definido como promoção. Preço: {} -> {}",
                    id, produto.getVlPreco(), vlPrecoPromocional);
        } else {
            produto.setIsPromocao(false);
            produto.setVlPrecoPromocional(null);
            log.info("Promoção removida do produto ID: {}", id);
        }

        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional(readOnly = true)
    public List<String> listarCategoriasPorRestaurante(Long restauranteId) {
        log.info("Listando categorias de produtos do restaurante ID: {}", restauranteId);

        if (!restauranteRepository.existsById(restauranteId)) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }

        List<Produto> produtos = produtoRepository.findByRestaurante_IdAndIsAtivoTrue(restauranteId);

        List<String> categorias = produtos.stream()
                .map(Produto::getCategoriaProduto)
                .filter(categoria -> categoria != null && !categoria.isBlank())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        log.debug("Encontradas {} categorias para o restaurante ID: {}", categorias.size(), restauranteId);

        return categorias;
    }

}
