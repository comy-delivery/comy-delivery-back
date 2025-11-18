package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.CupomRequestDTO;
import com.comy_delivery_back.dto.response.CupomResponseDTO;
import com.comy_delivery_back.service.CupomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/cupom")
public class CupomController {

    private final CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @PostMapping
    public ResponseEntity<CupomResponseDTO> criar(@Valid @RequestBody CupomRequestDTO dto) {
        CupomResponseDTO response = cupomService.criarCupom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CupomResponseDTO> buscarPorId(@PathVariable Long id) {
        CupomResponseDTO response = cupomService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CupomResponseDTO> buscarPorCodigo(@PathVariable String codigo) {
        CupomResponseDTO response = cupomService.buscarPorCodigo(codigo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CupomResponseDTO>> listarAtivos() {
        List<CupomResponseDTO> response = cupomService.listarAtivos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<CupomResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<CupomResponseDTO> response = cupomService.listarPorRestaurante(restauranteId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{codigo}/validar")
    public ResponseEntity<Boolean> validar(@PathVariable String codigo, @RequestParam Double valorPedido) {
        Boolean valido = cupomService.validarCupom(codigo, valorPedido);
        return ResponseEntity.status(HttpStatus.OK).body(valido);
    }

    @GetMapping("/{id}/verificar-validade")
    public ResponseEntity<Boolean> verificarValidade(@PathVariable Long id) {
        Boolean response = cupomService.verificarValidade(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/aplicar-desconto")
    public ResponseEntity<Double> aplicarDesconto(
            @PathVariable Long id,
            @RequestParam Double valorPedido) {
        Double response = cupomService.aplicarDesconto(id, valorPedido);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/incrementar-uso")
    public ResponseEntity<Void> incrementarUso(@PathVariable Long id) {
        cupomService.incrementarUso(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CupomResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CupomRequestDTO dto) {
        CupomResponseDTO response = cupomService.atualizarCupom(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        cupomService.desativarCupom(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cupomService.deletarCupom(id);
        return ResponseEntity.noContent().build();
    }

}
