package com.comy_delivery_back.controller;

import com.comy_delivery_back.client.ApiCepClient;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ApiCepDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.exception.CepNaoEncontradoException;
import com.comy_delivery_back.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

    private final ApiCepClient apiCepClient;
    private final EnderecoService enderecoService;

    public EnderecoController(ApiCepClient apiCepClient, EnderecoService enderecoService) {
        this.apiCepClient = apiCepClient;
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<ApiCepDTO> buscarPorCep(@PathVariable("cep") String cep) {
        return this.apiCepClient.buscarCep(cep);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEndereco(@PathVariable("id") Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(@RequestBody@Valid EnderecoRequestDTO endereco) throws CepNaoEncontradoException {
        EnderecoResponseDTO response = this.enderecoService.cadastrarEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(@PathVariable("id") Long id) {
        EnderecoResponseDTO response = this.enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable("id") Long id,
                                                               @RequestBody@Valid EnderecoRequestDTO enderecoDTO) throws CepNaoEncontradoException {
        var response = enderecoService.alterarEndereco(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
