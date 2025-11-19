package com.comy_delivery_back.service;

import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.response.EntregadorResponseDTO;
import com.comy_delivery_back.model.Entregador;
import com.comy_delivery_back.repository.EntregadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EntregadorService {

    private final EntregadorRepository entregadorRepository;

    public EntregadorService(EntregadorRepository entregadorRepository) {
        this.entregadorRepository = entregadorRepository;
    }

    @Transactional
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

        return new EntregadorResponseDTO(novoEntregador);
    }

    @Transactional
    public EntregadorResponseDTO buscarEntregadorPorId(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));

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
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));

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
    public void marcarComoDisponivel(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(()-> new IllegalArgumentException("entregador nao encontrado."));

        entregador.setDisponivel(true);
    }

    public void marcarComoIndisponivel(Long idEntregador){
        Entregador entregador = entregadorRepository.findById(idEntregador)
                .orElseThrow(()-> new IllegalArgumentException("entregador nao encontrado."));

        entregador.setDisponivel(true);
    }

    //enum iniciada
    public void iniciarEntrega(Long idEntrega){


    }

    //enum concluida
    public void finalizarEntrega(Long idEntrega){

    }
}
