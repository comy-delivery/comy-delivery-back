package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.PedidoRequestDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criar(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO response = pedidoService.criarPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        PedidoResponseDTO response = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<PedidoResponseDTO> response = pedidoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusPedido status) {
        PedidoResponseDTO response = pedidoService.atualizarStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @RequestParam String motivo) {
        pedidoService.cancelarPedido(id, motivo);
        return ResponseEntity.noContent().build();
    }

}
