package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.AdicionalRequestDTO;
import com.comy_delivery_back.dto.response.AdicionalResponseDTO;
import com.comy_delivery_back.exception.AdicionalNaoEncontradoException;
import com.comy_delivery_back.exception.ProdutoNaoEncontradoException;
import com.comy_delivery_back.model.Adicional;
import com.comy_delivery_back.model.Produto;
import com.comy_delivery_back.repository.AdicionalRepository;
import com.comy_delivery_back.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdicionalService {

    private final AdicionalRepository adicionalRepository;
    private final ProdutoRepository produtoRepository;

    public AdicionalService(AdicionalRepository adicionalRepository, ProdutoRepository produtoRepository) {
        this.adicionalRepository = adicionalRepository;
        this.produtoRepository = produtoRepository;
    }


    @Transactional
    public AdicionalResponseDTO criarAdicional(AdicionalRequestDTO dto) {
        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(dto.produtoId()));

        Adicional adicional = new Adicional();
        log.info("Criando o adicional:{}", dto.nmAdicional());
        BeanUtils.copyProperties(dto, adicional);

        adicional.setDsAdicional(dto.dsAdicional());
        adicional.setVlPrecoAdicional(dto.vlPrecoAdicional());
        adicional.setProduto(produto);
        Adicional adicionalSalvo = adicionalRepository.save(adicional);
        log.debug("Adicional salvo:{}", adicionalSalvo);

        return new AdicionalResponseDTO(adicionalSalvo);
    }

    @Transactional
    public AdicionalResponseDTO buscarPorId(Long id) {
       return  this.adicionalRepository.findById(id).map(AdicionalResponseDTO::new)
                .orElseThrow(() -> new AdicionalNaoEncontradoException(id));

    }

    @Transactional(readOnly = true)
    public List<AdicionalResponseDTO> listarPorProduto(Long produtoId) {
        return adicionalRepository.findByProduto_IdProduto(produtoId).stream()
                .map(AdicionalResponseDTO:: new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<AdicionalResponseDTO> listarDisponiveis() {
        return adicionalRepository.findByIsDisponivelTrue().stream()
                .map(AdicionalResponseDTO:: new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AdicionalResponseDTO atualizarAdicional(Long id, AdicionalRequestDTO dto) {
        Adicional adicional = adicionalRepository.findById(id)
                .orElseThrow(() -> new AdicionalNaoEncontradoException(id));

        Produto produto = produtoRepository.findById(dto.produtoId())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(dto.produtoId()));


        adicional.setNmAdicional(dto.nmAdicional());
        adicional.setDsAdicional(dto.dsAdicional());
        adicional.setVlPrecoAdicional(dto.vlPrecoAdicional());
        adicional.setProduto(produto);

        adicional = adicionalRepository.save(adicional);
        return new AdicionalResponseDTO(adicionalRepository.save(adicional));
    }

    @Transactional
    public void ativarAdicional(Long id) {
        Adicional adicional = adicionalRepository.findById(id)
                .orElseThrow(() -> new AdicionalNaoEncontradoException(id));
        adicional.setDisponivel(true);
        adicionalRepository.save(adicional);
    }

    @Transactional
    public void desativarAdicional(Long id) {
        Adicional adicional = adicionalRepository.findById(id)
                .orElseThrow(() -> new AdicionalNaoEncontradoException(id));
        adicional.setDisponivel(false);
        adicionalRepository.save(adicional);
    }

    @Transactional
    public void deletarAdicional(Long id) {
        adicionalRepository.deleteById(id);
    }

}
