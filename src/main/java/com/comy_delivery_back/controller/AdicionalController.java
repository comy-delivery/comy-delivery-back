package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AdicionalRequestDTO;
import com.comy_delivery_back.dto.response.AdicionalResponseDTO;
import com.comy_delivery_back.service.AdicionalService;
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

import java.util.List;

@RestController
@RequestMapping("/api/adicional")
@Tag(name = "Adicional", description = "Endpoints para gerenciamento de adicionais de produtos")
public class AdicionalController {

    private final AdicionalService adicionalService;

    public AdicionalController(AdicionalService adicionalService) {
        this.adicionalService = adicionalService;
    }

    @PostMapping
    @Operation(summary = "Criar novo adicional",
            description = "Cadastra um novo adicional para um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Adicional criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AdicionalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<AdicionalResponseDTO> criar(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do adicional a ser criado",
                    required = true
            ) AdicionalRequestDTO dto) {
        AdicionalResponseDTO response = adicionalService.criarAdicional(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar adicional por ID",
            description = "Retorna os detalhes de um adicional específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicional encontrado",
                    content = @Content(schema = @Schema(implementation = AdicionalResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Adicional não encontrado")
    })
    public ResponseEntity<AdicionalResponseDTO> buscarPorId(
            @Parameter(description = "ID do adicional", example = "1", required = true)
            @PathVariable Long id) {
        AdicionalResponseDTO response = adicionalService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/produto/{produtoId}")
    @Operation(summary = "Listar adicionais por produto",
            description = "Retorna todos os adicionais disponíveis para um produto específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de adicionais retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<List<AdicionalResponseDTO>> listarPorProduto(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long produtoId) {
        List<AdicionalResponseDTO> response = adicionalService.listarPorProduto(produtoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar adicionais disponíveis",
            description = "Retorna todos os adicionais marcados como disponíveis no sistema")
    @ApiResponse(responseCode = "200", description = "Lista de adicionais disponíveis")
    public ResponseEntity<List<AdicionalResponseDTO>> listarDisponiveis() {
        List<AdicionalResponseDTO> response = adicionalService.listarDisponiveis();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar adicional",
            description = "Atualiza os dados de um adicional existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicional atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AdicionalResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Adicional não encontrado")
    })
    public ResponseEntity<AdicionalResponseDTO> atualizar(
            @Parameter(description = "ID do adicional", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do adicional",
                    required = true
            ) AdicionalRequestDTO dto) {
        AdicionalResponseDTO response = adicionalService.atualizarAdicional(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar adicional",
            description = "Marca um adicional como disponível para uso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicional ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Adicional não encontrado")
    })
    public ResponseEntity<Void> ativar(
            @Parameter(description = "ID do adicional", example = "1", required = true)
            @PathVariable Long id) {
        adicionalService.ativarAdicional(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar adicional",
            description = "Marca um adicional como indisponível para uso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicional desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Adicional não encontrado")
    })
    public ResponseEntity<Void> desativar(
            @Parameter(description = "ID do adicional", example = "1", required = true)
            @PathVariable Long id) {
        adicionalService.desativarAdicional(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar adicional",
            description = "Remove permanentemente um adicional do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Adicional deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Adicional não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do adicional", example = "1", required = true)
            @PathVariable Long id) {
        adicionalService.deletarAdicional(id);
        return ResponseEntity.noContent().build();
    }
}
