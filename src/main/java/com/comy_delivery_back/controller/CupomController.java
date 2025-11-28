package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.CupomRequestDTO;
import com.comy_delivery_back.dto.response.CupomResponseDTO;
import com.comy_delivery_back.service.CupomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cupom")
@Tag(name = "Cupom", description = "Endpoints para gerenciamento de cupons de desconto")
public class CupomController {

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @PostMapping
    @Operation(summary = "Criar cupom",
            description = "Cadastra um novo cupom de desconto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cupom criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CupomResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Código do cupom já existe")
    })
    public ResponseEntity<CupomResponseDTO> criar(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do cupom a ser criado",
                    required = true
            ) CupomRequestDTO dto) {
        CupomResponseDTO response = cupomService.criarCupom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cupom por ID",
            description = "Retorna os detalhes de um cupom específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado",
                    content = @Content(schema = @Schema(implementation = CupomResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<CupomResponseDTO> buscarPorId(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id) {
        CupomResponseDTO response = cupomService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar cupom por código",
            description = "Retorna os detalhes de um cupom pelo seu código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado",
                    content = @Content(schema = @Schema(implementation = CupomResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<CupomResponseDTO> buscarPorCodigo(
            @Parameter(description = "Código do cupom", example = "BEMVINDO10", required = true)
            @PathVariable String codigo) {
        CupomResponseDTO response = cupomService.buscarPorCodigo(codigo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar cupons ativos",
            description = "Retorna todos os cupons ativos no sistema")
    @ApiResponse(responseCode = "200", description = "Lista de cupons ativos")
    public ResponseEntity<List<CupomResponseDTO>> listarAtivos() {
        List<CupomResponseDTO> response = cupomService.listarAtivos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    @Operation(summary = "Listar cupons por restaurante",
            description = "Retorna todos os cupons de um restaurante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cupons do restaurante"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    public ResponseEntity<List<CupomResponseDTO>> listarPorRestaurante(
            @Parameter(description = "ID do restaurante", example = "1", required = true)
            @PathVariable Long restauranteId) {
        List<CupomResponseDTO> response = cupomService.listarPorRestaurante(restauranteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{codigo}/validar")
    @Operation(summary = "Validar cupom",
            description = "Verifica se um cupom pode ser usado para um determinado valor de pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom válido"),
            @ApiResponse(responseCode = "400", description = "Cupom inválido, expirado ou não atende aos requisitos"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Boolean> validar(
            @Parameter(description = "Código do cupom", example = "BEMVINDO10", required = true)
            @PathVariable String codigo,
            @Parameter(description = "Valor do pedido", example = "50.00", required = true)
            @RequestParam BigDecimal valorPedido) {
        Boolean valido = cupomService.validarCupom(codigo, valorPedido);
        return ResponseEntity.status(HttpStatus.OK).body(valido);
    }

    @GetMapping("/{id}/verificar-validade")
    @Operation(summary = "Verificar validade do cupom",
            description = "Verifica se um cupom está ativo e dentro do prazo de validade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status de validade retornado"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Boolean> verificarValidade(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id) {
        Boolean response = cupomService.verificarValidade(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/aplicar-desconto")
    @Operation(summary = "Aplicar desconto do cupom",
            description = "Calcula o valor do desconto que será aplicado ao pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Desconto calculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cupom inválido para este pedido"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<BigDecimal> aplicarDesconto(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id,
            @Parameter(description = "Valor do pedido", example = "50.00", required = true)
            @RequestParam BigDecimal valorPedido) {
        BigDecimal response = cupomService.aplicarDesconto(id, valorPedido);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/incrementar-uso")
    @Operation(summary = "Incrementar uso do cupom",
            description = "Aumenta o contador de uso do cupom (usado automaticamente ao criar pedido)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Uso incrementado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Void> incrementarUso(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id) {
        cupomService.incrementarUso(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cupom",
            description = "Atualiza os dados de um cupom existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CupomResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<CupomResponseDTO> atualizar(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do cupom",
                    required = true
            ) CupomRequestDTO dto) {
        CupomResponseDTO response = cupomService.atualizarCupom(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar cupom",
            description = "Marca um cupom como inativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Void> desativar(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id) {
        cupomService.desativarCupom(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar cupom",
            description = "Marca um cupom como ativo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Void> ativar(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id) {
        cupomService.ativarCupom(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cupom",
            description = "Remove permanentemente um cupom do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cupom deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do cupom", example = "1", required = true)
            @PathVariable Long id) {
        cupomService.deletarCupom(id);
        return ResponseEntity.noContent().build();
    }
}
