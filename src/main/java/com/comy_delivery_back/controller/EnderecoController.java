package com.comy_delivery_back.controller;

import com.comy_delivery_back.client.ApiCepClient;
import com.comy_delivery_back.dto.request.AtualizarEnderecoRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ApiCepDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.exception.CepNaoEncontradoException;
import com.comy_delivery_back.service.ClienteService;
import com.comy_delivery_back.service.EnderecoService;
import com.comy_delivery_back.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/endereco")
public class EnderecoController {

    private final ApiCepClient apiCepClient;
    private final EnderecoService enderecoService;
    private final RestauranteService restauranteService;
    private final ClienteService clienteService;

    public EnderecoController(ApiCepClient apiCepClient, EnderecoService enderecoService, RestauranteController restauranteController, ClienteController clienteController, RestauranteService restauranteService, ClienteService clienteService) {
        this.apiCepClient = apiCepClient;
        this.enderecoService = enderecoService;
        this.restauranteService = restauranteService;
        this.clienteService = clienteService;
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
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(@RequestBody@Valid EnderecoRequestDTO endereco) {
        EnderecoResponseDTO response = this.enderecoService.cadastrarEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(@PathVariable("id") Long id) {
        EnderecoResponseDTO response = this.enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/alterar/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@PathVariable("id") Long id,
                                                               @RequestBody@Valid AtualizarEnderecoRequestDTO enderecoDTO) {
        var response = enderecoService.alterarEndereco(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/padrao")
    public ResponseEntity<Void> definirComoPadrao(@PathVariable Long id) {
        enderecoService.definirComoPadrao(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Vincular endereço existente a um usuário",
            description = "Associa um endereço já cadastrado (ID) a um Cliente ou Restaurante.")
    @PatchMapping("/{idEndereco}/vincular")
    public ResponseEntity<EnderecoResponseDTO> vincularEnderecoExistente(
            @PathVariable Long idEndereco,
            @RequestParam Long usuarioId,
            @RequestParam String tipoUsuario) {

        EnderecoResponseDTO response;

        if ("CLIENTE".equalsIgnoreCase(tipoUsuario)) {
            response = clienteService.vincularEnderecoExistente(usuarioId, idEndereco);

        } else if ("RESTAURANTE".equalsIgnoreCase(tipoUsuario)) {
            response = restauranteService.vincularEnderecoExistente(usuarioId, idEndereco);

        } else {
            throw new IllegalArgumentException("Tipo de usuário inválido. Use 'CLIENTE' ou 'RESTAURANTE'.");
        }

        return ResponseEntity.ok(response);
    }

}
