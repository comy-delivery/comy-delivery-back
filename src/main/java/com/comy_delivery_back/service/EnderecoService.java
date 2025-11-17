package com.comy_delivery_back.service;

import com.comy_delivery_back.client.ApiCepClient;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ApiCepDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.exception.CepNaoEncontradoException;
import com.comy_delivery_back.exception.EnderecoNaoEncontradoException;
import com.comy_delivery_back.model.Endereco;
import com.comy_delivery_back.repository.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        ApiCepDTO endereco = this.apiCepClient.buscarCep(enderecoDTO.cep()).getBody();

        if (endereco == null || endereco.cep() == null) {
            throw new CepNaoEncontradoException(enderecoDTO.cep());
        }

        var novo = new Endereco();
        novo.setCep(endereco.cep().replace("-", ""));
        novo.setBairro(endereco.district());
        novo.setLogradouro(endereco.address());
        novo.setCidade(endereco.city());
        novo.setEstado(endereco.state());
        novo.setLatitude(Double.valueOf(endereco.lat()));
        novo.setLongitude(Double.valueOf(endereco.lng()));

        novo.setNumero(enderecoDTO.numero());
        novo.setComplemento(enderecoDTO.complemento());
        novo.setTipoEndereco(enderecoDTO.tipoEndereco());
        novo.setPontoDeReferecia(enderecoDTO.pontoDeReferecia());

        Endereco enderecoSalvo = enderecoRepository.save(novo);
        return new EnderecoResponseDTO(enderecoSalvo);
    }

    @Transactional
    public EnderecoResponseDTO buscarEnderecoPorId(Long idEndereco){
        return this.enderecoRepository.findByIdEndereco(idEndereco)
                .map(EnderecoResponseDTO::new)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));
    }

    @Transactional
    public List<EnderecoResponseDTO> listarPorCliente(Long clienteId) {
        return enderecoRepository.findByCliente_IdUsuario(clienteId).stream()
                .map(EnderecoResponseDTO::new)
                .collect(Collectors.toList());
    }



    @Transactional
    public EnderecoResponseDTO alterarEndereco(Long idEndereco, EnderecoRequestDTO enderecoDTO)
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

        BeanUtils.copyProperties(enderecoDTO, enderecoParaAtualizar);

        Endereco enderecoSalvo = enderecoRepository.save(enderecoParaAtualizar);
        return new EnderecoResponseDTO(enderecoSalvo);
    }

    @Transactional
    public void deletarEndereco(Long idEndereco){
        Endereco endereco = enderecoRepository.findByIdEndereco(idEndereco)
                .orElseThrow(() -> new EnderecoNaoEncontradoException(idEndereco));
        enderecoRepository.delete(endereco);
    }



}
