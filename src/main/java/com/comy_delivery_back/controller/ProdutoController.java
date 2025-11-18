package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.ProdutoRequestDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestPart("produto") @Valid ProdutoRequestDTO dto,
                                                    @RequestPart("imagem") MultipartFile imagem) {

        if (imagem.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {

            ProdutoResponseDTO response = produtoService.criarProduto(dto, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO response = produtoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/promocoes")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPromocoes() {
        List<ProdutoResponseDTO> response = produtoService.listarPromocoes();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestPart("produto") @Valid ProdutoRequestDTO dto,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) {

        try {
            ProdutoResponseDTO response = produtoService.atualizarProduto(id, dto, imagem);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

}
