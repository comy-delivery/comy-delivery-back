package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.request.RestauranteRequestDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.ProdutoResponseDTO;
import com.comy_delivery_back.dto.response.RestauranteResponseDTO;
import com.comy_delivery_back.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurante")
@Tag(name = "Restaurante", description = "Operações de cadastro, consulta e gestão de restaurantes")
public class RestauranteController {
    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Operation(summary = "Criar um novo restaurante com logo",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso",
                            content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "CNPJ, E-mail ou Username já cadastrado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestauranteResponseDTO> cadastrarRestaurante(
            @RequestPart("restaurante") @Valid RestauranteRequestDTO restauranteRequestDTO,
            @RequestPart(value = "imagemLogo", required = false) MultipartFile imagem) throws IOException {

        RestauranteResponseDTO responseDTO = restauranteService.cadastrarRestaurante(restauranteRequestDTO, imagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "Buscar restaurante por ID",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorId(@PathVariable Long id) {
        RestauranteResponseDTO responseDTO = restauranteService.buscarRestaurantePorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Buscar restaurante por CNPJ",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "CNPJ não encontrado")
            })
    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<RestauranteResponseDTO> buscarRestaurantePorCnpj(@PathVariable String cnpj) {
        RestauranteResponseDTO responseDTO = restauranteService.buscarRestaurantePorCnpj(cnpj);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Atualizar dados do restaurante e/ou logo",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Email já cadastrado para outro restaurante")
            })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestauranteResponseDTO> atualizarRestaurante(@PathVariable Long id,
                                                                       @RequestPart("restaurante") RestauranteRequestDTO restauranteRequestDTO,
                                                                       @RequestPart(value = "imagemLogo", required = false) MultipartFile imagem) throws IOException {

        RestauranteResponseDTO responseDTO = restauranteService.atualizarRestaurante(id, restauranteRequestDTO, imagem);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Deletar (Inativar) um restaurante pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sucesso (Inativado)"),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable Long id) {
        restauranteService.deletarRestaurante(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Atualizar endereço existente do restaurante",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante/Endereço não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Endereço não pertence ao restaurante")
            })
    @PutMapping("/{idRestaurante}/enderecos/{idEndereco}")
    public ResponseEntity<EnderecoResponseDTO> alterarEnderecoRestaurante(@PathVariable Long idRestaurante,
                                                                          @PathVariable Long idEndereco,
                                                                          @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {

        EnderecoResponseDTO responseDTO = restauranteService.alterarEnderecoRestaurante(idRestaurante, idEndereco, enderecoRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }


    @Operation(summary = "Listar todos os restaurantes que estão abertos",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RestauranteResponseDTO.class)))
            })
    @GetMapping("/abertos")
    public ResponseEntity<List<RestauranteResponseDTO>> listarRestaurantesAbertos() {
        List<RestauranteResponseDTO> abertos = restauranteService.listarRestaurantesAbertos();
        return ResponseEntity.ok(abertos);
    }

    @Operation(summary = "Listar Endereços do Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            })
    @GetMapping("/{idRestaurante}/enderecos")
    public ResponseEntity<List<EnderecoResponseDTO>> listarEnderecosDoRestaurante(@PathVariable Long idRestaurante) {
        List<EnderecoResponseDTO> response = restauranteService.listarEnderecosRestaurante(idRestaurante);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar produtos de um restaurante específico",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProdutoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            })
    @GetMapping("/{idRestaurante}/produtos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutosRestaurante(@PathVariable Long idRestaurante) {
        List<ProdutoResponseDTO> produtos = restauranteService.listarProdutosRestaurante(idRestaurante);
        return ResponseEntity.ok(produtos);
    }


    @Operation(summary = "Abrir restaurante manualmente",
            responses = @ApiResponse(responseCode = "204", description = "Status alterado com sucesso"))
    @PatchMapping("/{id}/status/abrir")
    public ResponseEntity<Void> abrirRestauranteManual(@PathVariable Long id) {
        restauranteService.abrirRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Fechar restaurante manualmente",
            responses = @ApiResponse(responseCode = "204", description = "Status alterado com sucesso"))
    @PatchMapping("/{id}/status/fechar")
    public ResponseEntity<Void> fecharRestauranteManual(@PathVariable Long id) {
        restauranteService.fecharRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Disponibilizar restaurante (para aparecer nas buscas)",
            responses = @ApiResponse(responseCode = "204", description = "Status alterado com sucesso"))
    @PatchMapping("/{id}/status/disponibilizar")
    public ResponseEntity<Void> disponibilizarRestaurante(@PathVariable Long id) {
        restauranteService.disponibilidarRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Indisponibilizar restaurante (para remover das buscas)",
            responses = @ApiResponse(responseCode = "204", description = "Status alterado com sucesso"))
    @PatchMapping("/{id}/status/indisponibilizar")
    public ResponseEntity<Void> indisponibilizarRestaurante(@PathVariable Long id) {
        restauranteService.indiponibilizarRestaurante(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Inicia o processo de recuperação de senha por e-mail",
            responses = {
                    @ApiResponse(responseCode = "200", description = "E-mail de recuperação enviado (ou será enviado)"),
                    @ApiResponse(responseCode = "400", description = "E-mail não fornecido")
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(example = "{\"email\": \"restaurante@exemplo.com\"}")))
    @PostMapping("/recuperacao/iniciar")
    public ResponseEntity<Void> iniciarRecuperacaoSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        restauranteService.iniciarRecuperacaoSenha(email);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Redefine a senha utilizando o token de recuperação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Token/Senha ausente"),
                    @ApiResponse(responseCode = "404", description = "Token inválido ou expirado")
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(example = "{\"novaSenha\": \"SenhaSegura123\"}")))
    @PostMapping("/recuperacao/redefinir")
    public ResponseEntity<Void> redefinirSenha(
            @Parameter(in = ParameterIn.QUERY, description = "Token de recuperação") @RequestParam String token,
            @RequestBody Map<String, String> request) {

        String novaSenha = request.get("novaSenha");
        if (novaSenha == null || novaSenha.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        restauranteService.redefinirSenha(token, novaSenha);

        return ResponseEntity.ok().build();
    }
}