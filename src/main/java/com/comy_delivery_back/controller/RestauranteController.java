package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AtualizarEnderecoRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.request.RedefinirSenhaRequestDTO;
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
            @RequestPart(value = "imagemLogo", required = false) MultipartFile imagemLogo,
            @RequestPart(value = "imagemBanner", required = false) MultipartFile imagemBanner) throws IOException {

        RestauranteResponseDTO responseDTO = restauranteService.cadastrarRestaurante(restauranteRequestDTO, imagemLogo, imagemBanner);
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
                                                                       @RequestPart(value = "imagemLogo", required = false) MultipartFile imagemLogo,
                                                                       @RequestPart(value = "imagemBanner", required = false) MultipartFile imagemBanner) throws IOException {

        RestauranteResponseDTO responseDTO = restauranteService.atualizarRestaurante(id, restauranteRequestDTO, imagemLogo, imagemBanner);
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
                                                                          @RequestBody @Valid AtualizarEnderecoRequestDTO enderecoRequestDTO) {

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

    @Operation(summary = "Listar Endereços do Restaurante",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
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

    @Operation(
            summary = "Inicia o processo de recuperação de senha por e-mail",
            responses = {
                    @ApiResponse(responseCode = "200", description = "E-mail de recuperação enviado (ou será enviado)"),
                    @ApiResponse(responseCode = "400", description = "E-mail não fornecido")
            }
    )
    @PostMapping("/recuperacao/iniciar")
    public ResponseEntity<Map<String, String>> iniciarRecuperacaoSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "O campo 'email' é obrigatório."));
        }

        restauranteService.iniciarRecuperacaoSenha(email);

        return ResponseEntity.ok(Map.of("message", "Se o email estiver cadastrado, um link de recuperação será enviado."));
    }

    @Operation(
            summary = "Redefine a senha utilizando o token de recuperação",
            description = "Recebe o token e a nova senha no corpo da requisição (mais seguro).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados da nova senha inválidos ou token ausente"),
                    @ApiResponse(responseCode = "404", description = "Token inválido ou expirado")
            }
    )
    @PostMapping("/recuperacao/redefinir")
    public ResponseEntity<Map<String, String>> redefinirSenha(
            @RequestBody @Valid RedefinirSenhaRequestDTO requestDTO) {

        restauranteService.redefinirSenha(requestDTO.token(), requestDTO.novaSenha());

        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso."));
    }

    @Operation(summary = "Upload/Atualizar Logo", description = "Envia o arquivo de imagem para ser a logo do restaurante.")
    @PutMapping(value = "/{id}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> atualizarLogo(
            @PathVariable Long id,
            @RequestPart("imagem") MultipartFile imagem) throws IOException {
        restauranteService.atualizarImagemLogo(id, imagem);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar Logo", description = "Retorna o binário da imagem da logo.")
    @GetMapping(value = "/{id}/logo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> buscarLogo(@PathVariable Long id) {
        byte[] imagem = restauranteService.buscarImagemLogo(id);
        if (imagem == null || imagem.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagem);
    }

    @Operation(summary = "Upload/Atualizar Banner", description = "Envia o arquivo de imagem para ser o banner do restaurante.")
    @PutMapping(value = "/{id}/banner", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> atualizarBanner(
            @PathVariable Long id,
            @RequestPart("imagem") MultipartFile imagem) throws IOException {
        restauranteService.atualizarImagemBanner(id, imagem);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar Banner", description = "Retorna o binário da imagem do banner.")
    @GetMapping(value = "/{id}/banner", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> buscarBanner(@PathVariable Long id) {
        byte[] imagem = restauranteService.buscarImagemBanner(id);
        if (imagem == null || imagem.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(imagem);
    }

    @Operation(summary = "Adicionar novo endereço ao restaurante",
            description = "Cria um novo endereço e o vincula ao restaurante informado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                            content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado"),
                    @ApiResponse(responseCode = "400", description = "Dados do endereço inválidos")
            })
    @PostMapping("/{id}/enderecos")
    public ResponseEntity<EnderecoResponseDTO> adicionarEndereco(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {

        EnderecoResponseDTO response = restauranteService.adicionarEnderecoRestaurante(id, enderecoRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Recalcular Tempo Médio de Entrega",
            description = "Calcula a média histórica do tempo total (da criação do pedido até a entrega) e atualiza o cadastro do restaurante.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tempo atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
            })
    @PatchMapping("/{id}/calcular-tempo-medio")
    public ResponseEntity<RestauranteResponseDTO> recalcularTempoMedio(@PathVariable Long id) {
        RestauranteResponseDTO response = restauranteService.atualizarTempoMedioEntrega(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Abrir todos os restaurantes",
            description = "Define o status de aberto como 'true' para todos os restaurantes que estão disponíveis no sistema.")
    @PatchMapping("/status/abrir-todos")
    public ResponseEntity<Void> abrirTodosRestaurantes() {
        restauranteService.abrirTodosRestaurantes();
        return ResponseEntity.noContent().build();
    }


}