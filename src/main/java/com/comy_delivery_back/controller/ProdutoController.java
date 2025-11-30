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
import java.math.BigDecimal;
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
                                                    @RequestPart("imagem") MultipartFile imagem) throws IOException {

            ProdutoResponseDTO response = produtoService.criarProduto(dto, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
            @RequestPart(value = "imagem", required = false) MultipartFile imagem) throws IOException {

            ProdutoResponseDTO response = produtoService.atualizarProduto(id, dto, imagem);
            return ResponseEntity.ok(response);

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

    @Operation(summary = "Upload/Atualizar Imagem do Produto")
    @PutMapping(value = "/{id}/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> atualizarImagem(
            @PathVariable Long id,
            @RequestPart("imagem") MultipartFile imagem) throws IOException {
        produtoService.atualizarImagemProduto(id, imagem);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar Imagem do Produto")
    @GetMapping(value = "/{id}/imagem", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> buscarImagem(@PathVariable Long id) {
        byte[] imagem = produtoService.buscarImagemProduto(id);
        if (imagem == null || imagem.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagem);
    }

    @Operation(summary = "Atualizar produto para promoção")
    @PatchMapping("/{id}/promocao")
    public ResponseEntity<ProdutoResponseDTO> atualizarPromocao(
            @PathVariable Long id,
            @RequestParam BigDecimal vlPrecoPromocional,
            @RequestParam(defaultValue = "true") Boolean isPromocao) {
        ProdutoResponseDTO response = produtoService.atualizarPromocao(id, vlPrecoPromocional, isPromocao);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar categorias de produtos de um restaurante")
    @GetMapping("/restaurante/{restauranteId}/categorias")
    public ResponseEntity<List<String>> listarCategoriasPorRestaurante(@PathVariable Long restauranteId) {
        List<String> categorias = produtoService.listarCategoriasPorRestaurante(restauranteId);
        return ResponseEntity.ok(categorias);
    }

}
