package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.response.EntregadorResponseDTO;
import com.comy_delivery_back.model.Entregador;
import com.comy_delivery_back.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EntregadorService {
    @Autowired
    private EntregadorRepository entregadorRepository;

    public EntregadorResponseDTO cadastrarEntregador(EntregadorRequestDTO entregadorRequestDTO){
        if (entregadorRepository.findByCpfEntregador(entregadorRequestDTO.cpfEntregador()).isPresent()){
            throw new RuntimeException("CPF vinculado a uma conta");
        }

        if (entregadorRepository.findByEmailEntregador(entregadorRequestDTO.emailEntregador()).isPresent()){
            throw new RuntimeException("Email vinculado a uma conta");
        }

        Entregador novoEntregador = new Entregador();

        novoEntregador.setUsername(entregadorRequestDTO.username());
        novoEntregador.setPassword(entregadorRequestDTO.password()); //encoder AQUI
        novoEntregador.setNmEntregador(entregadorRequestDTO.nmEntregador());
        novoEntregador.setEmailEntregador(entregadorRequestDTO.emailEntregador());
        novoEntregador.setCpfEntregador(entregadorRequestDTO.cpfEntregador());
        novoEntregador.setTelefoneEntregador(entregadorRequestDTO.telefoneEntregador());
        novoEntregador.setVeiculo(entregadorRequestDTO.veiculo());
        novoEntregador.setPlaca(entregadorRequestDTO.placa());

        return new EntregadorResponseDTO(
                novoEntregador.getId(),
                novoEntregador.getUsername(),
                novoEntregador.getNmEntregador(),
                novoEntregador.getEmailEntregador(),
                novoEntregador.getCpfEntregador(),
                novoEntregador.getTelefoneEntregador(),
                novoEntregador.getVeiculo(),
                novoEntregador.getPlaca(),
                novoEntregador.getDataCadastroEntregador(),
                //novoEntregador.getEntregasEntregador(),
                novoEntregador.isAtivo()
        );
    }

    public EntregadorResponseDTO buscarEntregadorPorId(Long idEntregador){
        var entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));

        return new EntregadorResponseDTO(
                entregador.getId(),
                entregador.getUsername(),
                entregador.getNmEntregador(),
                entregador.getEmailEntregador(),
                entregador.getCpfEntregador(),
                entregador.getTelefoneEntregador(),
                entregador.getVeiculo(),
                entregador.getPlaca(),
                entregador.getDataCadastroEntregador(),
                //novoEntregador.getEntregasEntregador(),
                entregador.isAtivo()

        );
    }
}
