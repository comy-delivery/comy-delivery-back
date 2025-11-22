package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.CupomRequestDTO;
import com.comy_delivery_back.dto.response.CupomResponseDTO;
import com.comy_delivery_back.exception.CupomInvalidoException;
import com.comy_delivery_back.exception.CupomNaoEncontradoException;
import com.comy_delivery_back.exception.RegistrosDuplicadosException;
import com.comy_delivery_back.exception.RestauranteNaoEncontradoException;
import com.comy_delivery_back.model.Cupom;
import com.comy_delivery_back.model.Restaurante;
import com.comy_delivery_back.repository.CupomRepository;
import com.comy_delivery_back.repository.RestauranteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CupomService {

    private final CupomRepository cupomRepository;
    private final RestauranteRepository restauranteRepository;

    public CupomService(CupomRepository cupomRepository, RestauranteRepository restauranteRepository) {
        this.cupomRepository = cupomRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional
    public CupomResponseDTO criarCupom(CupomRequestDTO dto) {
        if (cupomRepository.findByCodigoCupom(dto.codigoCupom()).isPresent()) {
            throw new RegistrosDuplicadosException("O cupom já existe com o código:"+dto.codigoCupom());
        }

        Cupom cupom = new Cupom();
        BeanUtils.copyProperties(dto, cupom);

        cupom.setCodigoCupom(dto.codigoCupom().toUpperCase());
        cupom.setVlDesconto(dto.vlDesconto());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        cupom.setVlMinimoPedido(dto.vlMinimoPedido());
        cupom.setQtdUsado(0);

        if (dto.restauranteId() != null) {
            Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                    .orElseThrow(() -> new RestauranteNaoEncontradoException(dto.restauranteId()));
            cupom.setRestaurante(restaurante);
        }
        return new CupomResponseDTO(cupomRepository.save(cupom));
    }

    @Transactional
    public CupomResponseDTO buscarPorId(Long id) {
        return this.cupomRepository.findById(id).map(CupomResponseDTO::new)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));
    }

    @Transactional
    public CupomResponseDTO buscarPorCodigo(String codigo) {
        Cupom cupom = cupomRepository.findByCodigoCupom(codigo.toUpperCase())
                .orElseThrow(() -> new CupomNaoEncontradoException(codigo));
        return new CupomResponseDTO(cupom);
    }

    @Transactional
    public List<CupomResponseDTO> listarAtivos() {
        return cupomRepository.findByIsAtivoTrue().stream()
                .map(CupomResponseDTO:: new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CupomResponseDTO> listarPorRestaurante(Long restauranteId) {
        return cupomRepository.findByRestaurante_Id(restauranteId).stream()
                .map(CupomResponseDTO:: new)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean validarCupom(String codigo, BigDecimal valorPedido) {
        log.debug("A validar cupom '{}' para pedido de valor {}", codigo, valorPedido);
        Cupom cupom = cupomRepository.findByCodigoCupom(codigo.toUpperCase())
                .orElseThrow(() -> new CupomNaoEncontradoException(codigo));

        if (!cupom.isAtivo()) {
            throw new CupomInvalidoException("Cupom inativo");
        }

        if (cupom.getDtValidade().isBefore(LocalDateTime.now())) {
            log.warn("Tentativa de uso de cupom expirado: {}", codigo);
            throw new CupomInvalidoException("Cupom expirado");
        }

        if (cupom.getQtdUsoMaximo() != null && cupom.getQtdUsado() >= cupom.getQtdUsoMaximo()) {
            throw new CupomInvalidoException("Cupom atingiu o limite de uso");
        }

        if (cupom.getVlMinimoPedido() != null && valorPedido.compareTo(cupom.getVlMinimoPedido()) < 0) {
            throw new CupomInvalidoException("Valor mínimo do pedido não atingido");
        }

        log.info("Cupom '{}' validado com sucesso.", codigo);
        return true;
    }

    @Transactional
    public void incrementarUso(Long idCupom) {
        Cupom cupom = cupomRepository.findById(idCupom)
                .orElseThrow(() -> new CupomNaoEncontradoException(idCupom));
        cupom.setQtdUsado(cupom.getQtdUsado() + 1);
        cupomRepository.save(cupom);
    }

    @Transactional
    public BigDecimal aplicarDesconto(Long id, BigDecimal valorPedido) {
        Cupom cupom = cupomRepository.findById(id)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));

        if (!cupom.isAtivo()) {
            throw new CupomInvalidoException("Cupom inativo");
        }

        if (cupom.getDtValidade().isBefore(LocalDateTime.now())) {
            throw new CupomInvalidoException("Cupom expirado");
        }

        if (cupom.getQtdUsoMaximo() != null && cupom.getQtdUsado() >= cupom.getQtdUsoMaximo()) {
            throw new CupomInvalidoException("Cupom atingiu o limite de uso");
        }

        if (cupom.getVlMinimoPedido() != null && valorPedido.compareTo(cupom.getVlMinimoPedido()) < 0) {
            throw new CupomInvalidoException("Valor do pedido é menor que o mínimo necessário para usar este cupom");
        }


        BigDecimal desconto = switch (cupom.getTipoCupom()) {
            case VALOR_FIXO -> cupom.getVlDesconto();
            case PERCENTUAL -> valorPedido
                    .multiply(cupom.getPercentualDesconto())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            default -> BigDecimal.ZERO;
        };


        if (desconto.compareTo(valorPedido) > 0) {
            desconto = valorPedido;
        }

        return desconto;
    }

    public Boolean verificarValidade(Long id) {
        Cupom cupom = cupomRepository.findById(id)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));

        if (!cupom.isAtivo()) {
            return false;
        }

        if (cupom.getDtValidade().isBefore(LocalDateTime.now())) {
            return false;
        }

        if (cupom.getQtdUsoMaximo() != null && cupom.getQtdUsado() >= cupom.getQtdUsoMaximo()) {
            return false;
        }

        return true;
    }

    @Transactional
    public CupomResponseDTO atualizarCupom(Long id, CupomRequestDTO dto) {
        Cupom cupom = cupomRepository.findById(id)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));

        BeanUtils.copyProperties(dto, cupom);
        return new CupomResponseDTO(cupomRepository.save(cupom));
    }

    @Transactional
    public void desativarCupom(Long id) {
        Cupom cupom = cupomRepository.findById(id)
                .orElseThrow(() -> new CupomNaoEncontradoException(id));
        cupom.setAtivo(false);
        cupomRepository.save(cupom);
    }

    @Transactional
    public void deletarCupom(Long id) {
        cupomRepository.deleteById(id);
    }



}
