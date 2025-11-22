package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AdicionalRequestDTO;
import com.comy_delivery_back.dto.response.AdicionalResponseDTO;
import com.comy_delivery_back.service.AdicionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adicional")
public class AdicionalController {

    private final AdicionalService adicionalService;

    public AdicionalController(AdicionalService adicionalService) {
        this.adicionalService = adicionalService;
    }

    @PostMapping
    public ResponseEntity<AdicionalResponseDTO> criar(@Valid @RequestBody AdicionalRequestDTO dto) {
        AdicionalResponseDTO response = adicionalService.criarAdicional(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdicionalResponseDTO> buscarPorId(@PathVariable Long id) {
        AdicionalResponseDTO response = adicionalService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<AdicionalResponseDTO>> listarPorProduto(@PathVariable Long produtoId) {
        List<AdicionalResponseDTO> response = adicionalService.listarPorProduto(produtoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<AdicionalResponseDTO>> listarDisponiveis() {
        List<AdicionalResponseDTO> response = adicionalService.listarDisponiveis();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdicionalResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AdicionalRequestDTO dto) {
        AdicionalResponseDTO response = adicionalService.atualizarAdicional(id, dto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        adicionalService.ativarAdicional(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        adicionalService.desativarAdicional(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        adicionalService.deletarAdicional(id);
        return ResponseEntity.noContent().build();
    }

}
