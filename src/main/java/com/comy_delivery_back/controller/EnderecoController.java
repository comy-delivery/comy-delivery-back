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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/endereco")
@Tag(name = "Endereço", description = "Endpoints para gerenciamento de endereços e consulta de CEP")
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
    @Operation(summary = "Buscar endereço por CEP",
            description = "Consulta informações de endereço através da API de CEP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = @Content(schema = @Schema(implementation = ApiCepDTO.class))),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado")
    })
    public ResponseEntity<ApiCepDTO> buscarPorCep(
            @Parameter(description = "CEP sem formatação (8 dígitos)", example = "01001000", required = true)
            @PathVariable("cep") String cep) {
        return this.apiCepClient.buscarCep(cep);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover endereço",
            description = "Remove um endereço do sistema (mínimo de 1 endereço por usuário)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Não é possível remover o último endereço"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<Void> removerEndereco(
            @Parameter(description = "ID do endereço", example = "1", required = true)
            @PathVariable("id") Long id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    @Operation(summary = "Adicionar novo endereço",
            description = "Cadastra um novo endereço no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "CEP não encontrado")
    })
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do endereço",
                    required = true
            ) EnderecoRequestDTO endereco) {
        EnderecoResponseDTO response = this.enderecoService.cadastrarEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar endereço por ID",
            description = "Retorna os detalhes de um endereço específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado",
                    content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<EnderecoResponseDTO> buscarEndereco(
            @Parameter(description = "ID do endereço", example = "1", required = true)
            @PathVariable("id") Long id) {
        EnderecoResponseDTO response = this.enderecoService.buscarEnderecoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/alterar/{id}")
    @Operation(summary = "Atualizar endereço",
            description = "Atualiza os dados de um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(
            @Parameter(description = "ID do endereço", example = "1", required = true)
            @PathVariable("id") Long id,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do endereço",
                    required = true
            ) AtualizarEnderecoRequestDTO enderecoDTO) {
        var response = enderecoService.alterarEndereco(id, enderecoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/padrao")
    @Operation(summary = "Definir endereço como padrão",
            description = "Define um endereço como principal do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço definido como padrão"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<Void> definirComoPadrao(
            @Parameter(description = "ID do endereço", example = "1", required = true)
            @PathVariable Long id) {
        enderecoService.definirComoPadrao(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{idEndereco}/vincular")
    @Operation(summary = "Vincular endereço existente a um usuário",
            description = "Associa um endereço já cadastrado a um Cliente ou Restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço vinculado com sucesso",
                    content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Tipo de usuário inválido ou endereço já vinculado"),
            @ApiResponse(responseCode = "404", description = "Endereço ou usuário não encontrado")
    })
    public ResponseEntity<EnderecoResponseDTO> vincularEnderecoExistente(
            @Parameter(description = "ID do endereço", example = "1", required = true)
            @PathVariable Long idEndereco,
            @Parameter(description = "ID do usuário (cliente ou restaurante)", example = "1", required = true)
            @RequestParam Long usuarioId,
            @Parameter(description = "Tipo de usuário", example = "CLIENTE", required = true,
                    schema = @Schema(allowableValues = {"CLIENTE", "RESTAURANTE"}))
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
