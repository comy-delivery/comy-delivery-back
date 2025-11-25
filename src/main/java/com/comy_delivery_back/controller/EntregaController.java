package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AtualizarStatusEntregaDTO;
import com.comy_delivery_back.dto.request.EntregaRequestDTO;
import com.comy_delivery_back.dto.response.EntregaResponseDTO;
import com.comy_delivery_back.enums.StatusEntrega;
import com.comy_delivery_back.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@Tag(name = "Entregas", description = "Endpoints para gestão de entregas e entregadores")
public class EntregaController {

    private final EntregaService entregaService;

    public EntregaController(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova entrega",
            description = "Cria uma nova entrega baseada no ID do pedido. O status inicial é PENDENTE.")
    @ApiResponse(responseCode = "201", description = "Entrega cadastrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<EntregaResponseDTO> cadastrarEntrega(@RequestBody @Valid EntregaRequestDTO requestDTO) {
        EntregaResponseDTO novaEntrega = entregaService.cadastrarEntrega(requestDTO);
        return new ResponseEntity<>(novaEntrega, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar status da entrega",
            description = "Permite a transição de status (PENDENTE -> EM_ROTA, EM_ROTA -> CONCLUIDA, etc.).")
    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Transição de status inválida ou dados ausentes (ex: entregadorId)")
    @ApiResponse(responseCode = "404", description = "Entrega, Pedido ou Entregador não encontrado")
    public ResponseEntity<EntregaResponseDTO> atualizarStatusEntrega(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusEntregaDTO requestDTO) {
        EntregaResponseDTO entregaAtualizada = entregaService.atualizarEntrega(id, requestDTO);
        return ResponseEntity.ok(entregaAtualizada);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar entrega por ID")
    @ApiResponse(responseCode = "200", description = "Entrega encontrada")
    @ApiResponse(responseCode = "404", description = "Entrega não encontrada")
    public ResponseEntity<EntregaResponseDTO> buscarEntregaPorId(@PathVariable Long id) {
        EntregaResponseDTO entrega = entregaService.buscarEntregaPorId(id);
        return ResponseEntity.ok(entrega);
    }

    @GetMapping("/pedido/{idPedido}")
    @Operation(summary = "Buscar entrega pelo ID do Pedido")
    public ResponseEntity<EntregaResponseDTO> buscarEntregaPorIdPedido(@PathVariable Long idPedido) {
        EntregaResponseDTO entrega = entregaService.buscarEntregaPorIdPedido(idPedido);
        return ResponseEntity.ok(entrega);
    }

    @GetMapping("/status")
    @Operation(summary = "Listar entregas por status")
    public ResponseEntity<List<EntregaResponseDTO>> buscarEntregasPorStatus(
            @RequestParam StatusEntrega status) {
        List<EntregaResponseDTO> entregas = entregaService.buscarEntregaPorStatus(status);
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/entregador/{idEntregador}")
    @Operation(summary = "Listar todas as entregas de um entregador")
    public ResponseEntity<List<EntregaResponseDTO>> buscarEntregasPorEntregador(@PathVariable Long idEntregador) {
        List<EntregaResponseDTO> entregas = entregaService.buscarEntregasPorEntregador(idEntregador);
        return ResponseEntity.ok(entregas);
    }

    @GetMapping("/entregador/{idEntregador}/status")
    @Operation(summary = "Listar entregas de um entregador por status específico")
    public ResponseEntity<List<EntregaResponseDTO>> buscarEntregasEntregadorPorStatus(
            @PathVariable Long idEntregador,
            @RequestParam StatusEntrega status) {
        List<EntregaResponseDTO> entregas = entregaService.buscarEntregaEntregadorPorStatus(idEntregador, status);
        return ResponseEntity.ok(entregas);
    }

    @Operation(summary = "Vincular Avaliação à Entrega",
            description = "Atualiza a nota da entrega com base em uma avaliação já cadastrada. Valida se ambos pertencem ao mesmo pedido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vínculo realizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entrega ou Avaliação não encontrada"),
                    @ApiResponse(responseCode = "400", description = "Avaliação não pertence ao pedido da entrega")
            })
    @PatchMapping("/{idEntrega}/avaliacao/{idAvaliacao}")
    public ResponseEntity<EntregaResponseDTO> vincularAvaliacao(
            @PathVariable Long idEntrega,
            @PathVariable Long idAvaliacao) {

        EntregaResponseDTO response = entregaService.vincularAvaliacao(idEntrega, idAvaliacao);
        return ResponseEntity.ok(response);
    }

}