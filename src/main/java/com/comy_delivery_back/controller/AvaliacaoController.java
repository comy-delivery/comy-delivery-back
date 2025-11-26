package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AvaliacaoRequestDTO;
import com.comy_delivery_back.dto.response.AvaliacaoResponseDTO;
import com.comy_delivery_back.service.AvaliacaoService;
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
@RequestMapping("/api/avaliacao")
@Tag(name = "Avaliação", description = "Endpoints para gerenciamento de avaliações de pedidos e entregas")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    @Operation(summary = "Adicionar avaliação",
            description = "Cria uma nova avaliação para um pedido e entregador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso",
                    content = @Content(schema = @Schema(implementation = AvaliacaoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou pedido não está no status ENTREGUE"),
            @ApiResponse(responseCode = "404", description = "Restaurante, cliente, pedido ou entregador não encontrado")
    })
    public ResponseEntity<AvaliacaoResponseDTO> adicionar(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da avaliação",
                    required = true
            ) AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO response = avaliacaoService.adicionarAvaliacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Listar avaliações por restaurante",
            description = "Retorna todas as avaliações de um restaurante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorRestaurante(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long restauranteId) {
        List<AvaliacaoResponseDTO> response = avaliacaoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar avaliação",
            description = "Remove uma avaliação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da avaliação", example = "1", required = true)
            @PathVariable Long id) {
        avaliacaoService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
