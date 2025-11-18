package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AvaliacaoRequestDTO;
import com.comy_delivery_back.dto.response.AvaliacaoResponseDTO;
import com.comy_delivery_back.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> adicionar(@Valid @RequestBody AvaliacaoRequestDTO dto) {
        AvaliacaoResponseDTO response = avaliacaoService.adicionarAvaliacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<AvaliacaoResponseDTO> response = avaliacaoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoService.deletarAvaliacao(id);
        return ResponseEntity.noContent().build();
    }

}
