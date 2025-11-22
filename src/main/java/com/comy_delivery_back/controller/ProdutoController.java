package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.ProdutoRequestDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto Controller", description = "Controller para as operações com o banco de dados para os produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Criar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto criado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoRequestDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Requisição não autorizada",content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
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


    @Operation(summary = "Buscar um produto cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto buscado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Requisição não autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "403", description = "Requisição proibida", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO response = produtoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Listar produtos cadastrados por restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos carregada com sucesso",content = {@Content(mediaType = "application/json")} ),
            @ApiResponse(responseCode = "204", description = "Lista de produtos carregada sem  conteúdo", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "401", description = "Requisição não autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "403", description = "Requisição proibida", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Lista de produtos não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> response = produtoService.listarPorRestaurante(restauranteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar produtos cadastrados por restaurante com promoção")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos carregada com sucesso",content = {@Content(mediaType = "application/json")} ),
            @ApiResponse(responseCode = "204", description = "Lista de produtos carregada sem  conteúdo", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "401", description = "Requisição não autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "403", description = "Requisição proibida", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Lista de produtos não encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping("/promocoes")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPromocoes() {
        List<ProdutoResponseDTO> response = produtoService.listarPromocoes();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar um produto cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoRequestDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Requisição não autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "403", description = "Requisição proibida", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
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

    @Operation(summary = "Deletar um produto cadastrado por um restaurante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Requisição não autorizada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "403", description = "Requisição proibida", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

}
