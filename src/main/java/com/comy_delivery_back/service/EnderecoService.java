package com.comy_delivery_back.service;

import com.comy_delivery_back.client.ApiCepClient;
import com.comy_delivery_back.dto.request.AtualizarEnderecoRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ApiCepDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.exception.CepNaoEncontradoException;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.exception.RegraDeNegocioException;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnderecoService {


    private final EnderecoRepository enderecoRepository;
    private final ApiCepClient apiCepClient;

    public EnderecoService(EnderecoRepository enderecoRepository, ApiCepClient apiCepClient) {
        this.enderecoRepository = enderecoRepository;
        this.apiCepClient = apiCepClient;
    }

    @Transactional
    public EnderecoResponseDTO cadastrarEndereco(EnderecoRequestDTO enderecoDTO)
            throws CepNaoEncontradoException {
        log.info("A buscar dados para o CEP: {}", enderecoDTO.cep());

        ApiCepDTO endereco = this.apiCepClient.buscarCep(enderecoDTO.cep()).getBody();

        if (endereco == null || endereco.cep() == null) {
            log.warn("CEP não encontrado na API externa: {}", enderecoDTO.cep());
            throw new CepNaoEncontradoException(enderecoDTO.cep());
        }

        log.debug("Endereço encontrado: {}, {}", endereco.address(), endereco.city());
        var novo = new Endereco();
        novo.setCep(endereco.cep().replace("-", ""));
        novo.setBairro(endereco.district());
        novo.setLogradouro(endereco.address());
        novo.setCidade(endereco.city());
        novo.setEstado(endereco.state());
        novo.setLatitude(Double.valueOf(endereco.lat()));
        novo.setLongitude(Double.valueOf(endereco.lng()));

        if (enderecoDTO.logradouro() != null && !enderecoDTO.logradouro().isBlank()) {
            novo.setLogradouro(enderecoDTO.logradouro());
        }
        if (enderecoDTO.bairro() != null && !enderecoDTO.bairro().isBlank()) {
            novo.setBairro(enderecoDTO.bairro());
        }
        if (enderecoDTO.cidade() != null && !enderecoDTO.cidade().isBlank()) {
            novo.setCidade(enderecoDTO.cidade());
        }
        if (enderecoDTO.estado() != null && !enderecoDTO.estado().isBlank()) {
            novo.setEstado(enderecoDTO.estado());
        }


        novo.setNumero(enderecoDTO.numero());
        novo.setComplemento(enderecoDTO.complemento());
        novo.setTipoEndereco(enderecoDTO.tipoEndereco());
        novo.setPontoDeReferencia(enderecoDTO.pontoDeReferencia());

        Endereco enderecoSalvo = enderecoRepository.save(novo);
        return new EnderecoResponseDTO(enderecoSalvo);
    }

    @Transactional
    public EnderecoResponseDTO buscarEnderecoPorId(Long idEndereco) {
        return this.enderecoRepository.findByIdEndereco(idEndereco)
                .map(EnderecoResponseDTO::new)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));
    }

    @Transactional
    public List<EnderecoResponseDTO> listarPorCliente(Long clienteId) {
        return enderecoRepository.findByCliente_Id(clienteId).stream()
                .map(EnderecoResponseDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public EnderecoResponseDTO alterarEndereco(Long idEndereco, AtualizarEnderecoRequestDTO enderecoDTO)
            throws CepNaoEncontradoException {

        Endereco enderecoParaAtualizar = this.enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));

        if (!Objects.equals(enderecoDTO.cep(), enderecoParaAtualizar.getCep())) {
            ApiCepDTO endereco = this.apiCepClient.buscarCep(enderecoDTO.cep()).getBody();

            if (endereco == null || endereco.cep() == null) {
                throw new CepNaoEncontradoException(enderecoDTO.cep());
            }

            enderecoParaAtualizar.setCep(endereco.cep().replace("-", ""));
            enderecoParaAtualizar.setBairro(endereco.district());
            enderecoParaAtualizar.setLogradouro(endereco.address());
            enderecoParaAtualizar.setCidade(endereco.city());
            enderecoParaAtualizar.setEstado(endereco.state());
            enderecoParaAtualizar.setLatitude(Double.valueOf(endereco.lat()));
            enderecoParaAtualizar.setLongitude(Double.valueOf(endereco.lng()));
        }

        if (enderecoDTO.logradouro() != null && !enderecoDTO.logradouro().isBlank()) {
            enderecoParaAtualizar.setLogradouro(enderecoDTO.logradouro());
        }

        if (enderecoDTO.numero() != null && !enderecoDTO.numero().isBlank()) {
            enderecoParaAtualizar.setNumero(enderecoDTO.numero());
        }

        if (enderecoDTO.complemento() != null && !enderecoDTO.complemento().isBlank()) {
            enderecoParaAtualizar.setComplemento(enderecoDTO.complemento());
        }

        if (enderecoDTO.bairro() != null && !enderecoDTO.bairro().isBlank()) {
            enderecoParaAtualizar.setBairro(enderecoDTO.bairro());
        }

        if (enderecoDTO.cidade() != null && !enderecoDTO.cidade().isBlank()) {
            enderecoParaAtualizar.setCidade(enderecoDTO.cidade());
        }

        if (enderecoDTO.estado() != null && !enderecoDTO.estado().isBlank()) {
            enderecoParaAtualizar.setEstado(enderecoDTO.estado());
        }

        if (enderecoDTO.pontoDeReferencia() != null && !enderecoDTO.pontoDeReferencia().isBlank()) {
            enderecoParaAtualizar.setPontoDeReferencia(enderecoDTO.pontoDeReferencia());
        }

        if (enderecoDTO.tipoEndereco() != null) {
            enderecoParaAtualizar.setTipoEndereco(enderecoDTO.tipoEndereco());
        }


        Endereco enderecoSalvo = enderecoRepository.save(enderecoParaAtualizar);
        return new EnderecoResponseDTO(enderecoSalvo);
    }

    @Transactional
    public void deletarEndereco(Long idEndereco) {
        Endereco enderecoAlvo = enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));

        List<Endereco> enderecosDoDono = new ArrayList<>();

        if (enderecoAlvo.getCliente() != null) {
            enderecosDoDono = enderecoRepository.findByCliente_Id(enderecoAlvo.getCliente().getId());
        } else if (enderecoAlvo.getRestaurante() != null) {
            enderecosDoDono = enderecoRepository.findByRestaurante_Id(enderecoAlvo.getRestaurante().getId());
        }

        if (!enderecosDoDono.isEmpty() && enderecosDoDono.size() <= 1) {
            throw new RegraDeNegocioException("Não é possível remover o endereço. O usuário deve possuir pelo menos um endereço cadastrado.");
        }

        if (enderecoAlvo.getCliente() != null && enderecoAlvo.isPadrao()) {


            Endereco novoPadrao = enderecosDoDono.stream()
                    .filter(e -> !e.getIdEndereco().equals(idEndereco))
                    .findFirst()
                    .orElseThrow(() -> new RegraDeNegocioException("Erro inconsistente: Nenhum outro endereço disponível para definir como padrão."));

            novoPadrao.setPadrao(true);
            enderecoRepository.save(novoPadrao);

            log.info("O endereço ID {} foi definido como novo padrão para o cliente.", novoPadrao.getIdEndereco());
        }
        enderecoRepository.delete(enderecoAlvo);
        log.info("Endereço ID {} removido com sucesso.", idEndereco);
    }

    @Transactional
    public void definirComoPadrao(Long idEndereco) {

        Endereco enderecoAlvo = enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));

        if (enderecoAlvo.getCliente() == null) {
            throw new RegraDeNegocioException("Este método serve apenas para endereços de clientes.");
        }

        Long clienteId = enderecoAlvo.getCliente().getId();

        List<Endereco> enderecosDoCliente = enderecoRepository.findByCliente_Id(clienteId);

        for (Endereco endereco : enderecosDoCliente) {
            if (endereco.getIdEndereco().equals(idEndereco)) {
                endereco.setPadrao(true);
            } else {
                endereco.setPadrao(false);
            }
        }
        enderecoRepository.saveAll(enderecosDoCliente);
    }


}
