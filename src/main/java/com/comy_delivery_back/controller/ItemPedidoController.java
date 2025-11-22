package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.ItemPedidoRequestDTO;
import com.comy_delivery_back.dto.response.ItemPedidoResponseDTO;
import com.comy_delivery_back.service.ItemPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/item")
@Tag(name = "Item Pedido Controller", description = "Endpoints para gerenciamento de itens de pedido")
public class ItemPedidoController {

    private final ItemPedidoService itemPedidoService;

    public ItemPedidoController(ItemPedidoService itemPedidoService) {
        this.itemPedidoService = itemPedidoService;
    }

    @Operation(summary = "Adicionar item a um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido ou produto não encontrado")
    })
    @PostMapping
    public ResponseEntity<ItemPedidoResponseDTO> adicionarItem(@Valid @RequestBody ItemPedidoRequestDTO dto) {
        ItemPedidoResponseDTO response = itemPedidoService.adicionarItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar item de pedido por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> buscarPorId(@PathVariable Long id) {
        ItemPedidoResponseDTO response = itemPedidoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar itens de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedidoResponseDTO>> listarPorPedido(@PathVariable Long pedidoId) {
        List<ItemPedidoResponseDTO> response = itemPedidoService.listarPorPedido(pedidoId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar quantidade de um item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade atualizada"),
            @ApiResponse(responseCode = "400", description = "Quantidade inválida"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PatchMapping("/{id}/quantidade")
    public ResponseEntity<ItemPedidoResponseDTO> atualizarQuantidade(
            @PathVariable Long id,
            @RequestParam Integer novaQuantidade) {
        ItemPedidoResponseDTO response = itemPedidoService.atualizarQuantidade(id, novaQuantidade);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar observação de um item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Observação atualizada"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PatchMapping("/{id}/observacao")
    public ResponseEntity<ItemPedidoResponseDTO> atualizarObservacao(
            @PathVariable Long id,
            @RequestParam String observacao) {
        ItemPedidoResponseDTO response = itemPedidoService.atualizarObservacao(id, observacao);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Adicionar adicionais a um item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicionais adicionados"),
            @ApiResponse(responseCode = "404", description = "Item ou adicional não encontrado")
    })
    @PatchMapping("/{id}/adicionais")
    public ResponseEntity<ItemPedidoResponseDTO> adicionarAdicionais(
            @PathVariable Long id,
            @RequestBody List<Long> adicionaisIds) {
        ItemPedidoResponseDTO response = itemPedidoService.adicionarAdicionais(id, adicionaisIds);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover adicionais de um item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adicionais removidos"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/{id}/adicionais")
    public ResponseEntity<ItemPedidoResponseDTO> removerAdicionais(
            @PathVariable Long id,
            @RequestBody List<Long> adicionaisIds) {
        ItemPedidoResponseDTO response = itemPedidoService.removerAdicionais(id, adicionaisIds);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Calcular subtotal de um item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subtotal calculado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{id}/subtotal")
    public ResponseEntity<BigDecimal> calcularSubtotal(@PathVariable Long id) {
        BigDecimal subtotal = itemPedidoService.calcularSubtotalItem(id);
        return ResponseEntity.ok(subtotal);
    }

    @Operation(summary = "Remover item de um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido"),
            @ApiResponse(responseCode = "400", description = "Pedido não pode ser modificado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        itemPedidoService.removerItem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Duplicar item em um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item duplicado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PostMapping("/{id}/duplicar")
    public ResponseEntity<ItemPedidoResponseDTO> duplicarItem(@PathVariable Long id) {
        ItemPedidoResponseDTO response = itemPedidoService.duplicarItem(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
