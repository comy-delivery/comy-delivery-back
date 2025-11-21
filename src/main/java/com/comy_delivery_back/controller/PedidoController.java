package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AceitarPedidoRequestDTO;
import com.comy_delivery_back.dto.request.PedidoRequestDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pedido")
@Tag(name = "Pedido Controller", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Criar novo pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Aceitar ou recusar um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido aceito/recusado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser aceito/recusado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/aceitar")
    public ResponseEntity<PedidoResponseDTO> aceitarOuRecusar(
            @PathVariable Long id,
            @Valid @RequestBody AceitarPedidoRequestDTO dto) {
        PedidoResponseDTO response = pedidoService.aceitarPedido(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recusar um pedido com motivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido recusado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser recusado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/recusar")
    public ResponseEntity<PedidoResponseDTO> recusar(
            @PathVariable Long id,
            @RequestParam String motivo) {
        PedidoResponseDTO response = pedidoService.recusarPedido(id, motivo);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar pedido por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar pedidos por cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar todos os pedidos de um restaurante")
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar pedidos pendentes de um restaurante")
    @GetMapping("/restaurante/{restauranteId}/pendentes")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosPendentes(@PathVariable Long restauranteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPedidosPendentes(restauranteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar pedidos aceitos de um restaurante")
    @GetMapping("/restaurante/{restauranteId}/aceitos")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosAceitos(@PathVariable Long restauranteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPedidosAceitos(restauranteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar pedidos recusados de um restaurante")
    @GetMapping("/restaurante/{restauranteId}/recusados")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosRecusados(@PathVariable Long restauranteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPedidosRecusados(restauranteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar status do pedido")
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido status) {
        PedidoResponseDTO response = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancelar pedido")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @RequestParam String motivo) {
        pedidoService.cancelarPedido(id, motivo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Calcular subtotal do pedido")
    @GetMapping("/{id}/subtotal")
    public ResponseEntity<Double> calcularSubtotal(@PathVariable Long id) {
        Double subtotal = pedidoService.calcularSubtotal(id);
        return ResponseEntity.ok(subtotal);
    }

    @Operation(summary = "Calcular total do pedido")
    @GetMapping("/{id}/total")
    public ResponseEntity<Double> calcularTotal(@PathVariable Long id) {
        Double total = pedidoService.calcularTotal(id);
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Calcular tempo estimado de entrega")
    @GetMapping("/{id}/tempo-estimado")
    public ResponseEntity<Integer> calcularTempoEstimado(@PathVariable Long id) {
        Integer tempo = pedidoService.calcularTempoEstimado(id);
        return ResponseEntity.ok(tempo);
    }

    @Operation(summary = "Finalizar pedido (marcar como entregue)")
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Boolean> finalizarPedido(@PathVariable Long id) {
        Boolean finalizado = pedidoService.finalizarPedido(id);
        return ResponseEntity.ok(finalizado);
    }
}
