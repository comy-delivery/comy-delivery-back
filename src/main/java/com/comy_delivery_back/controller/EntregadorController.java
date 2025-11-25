package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.EntregadorRequestDTO;
import com.comy_delivery_back.dto.request.RedefinirSenhaRequestDTO;
import com.comy_delivery_back.dto.response.EntregadorResponseDTO;
import com.comy_delivery_back.service.EntregadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entregador")
@Tag(name = "Entregador", description = "Endpoints para gestão de entregadores e ações de entregador")
public class EntregadorController {

    private final EntregadorService entregadorService;

    public EntregadorController(EntregadorService entregadorService) {
        this.entregadorService = entregadorService;
    }

    @Operation(
            summary = "Cadastrar Entregador",
            description = "Cria um novo registro de entregador no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entregador criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos ou registros duplicados (CPF/Email)")
            }
    )
    @PostMapping
    public ResponseEntity<EntregadorResponseDTO> cadastrarEntregador(@RequestBody @Valid EntregadorRequestDTO requestDTO) {
        EntregadorResponseDTO novoEntregador = entregadorService.cadastrarEntregador(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEntregador);
    }

    @Operation(
            summary = "Buscar Entregador",
            description = "Retorna os dados de um entregador específico pelo ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dados do entregador retornados"),
                    @ApiResponse(responseCode = "404", description = "Entregador não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EntregadorResponseDTO> buscarEntregadorPorId(@PathVariable Long id) {
        EntregadorResponseDTO entregador = entregadorService.buscarEntregadorPorId(id);
        return ResponseEntity.ok(entregador);
    }

    @Operation(summary = "Listar Disponíveis", description = "Retorna a lista de entregadores marcados como disponíveis.")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<EntregadorResponseDTO>> listarEntregadoresDisponiveis() {
        List<EntregadorResponseDTO> lista = entregadorService.listarEntregadoresDisponiveis();
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Atualizar Dados",
            description = "Atualiza dados cadastrais de um entregador.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entregador atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entregador não encontrado")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EntregadorResponseDTO> atualizarDadosEntregador(@PathVariable Long id,
                                                                          @RequestBody EntregadorRequestDTO requestDTO) {
        EntregadorResponseDTO atualizado = entregadorService.atualizarDadosEntregador(id, requestDTO);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(
            summary = "Desativar Entregador",
            description = "Realiza a exclusão lógica (soft delete) do entregador.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Entregador desativado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entregador não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEntregador(@PathVariable Long id) {
        entregadorService.deletarEntregador(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Marcar como Disponível",
            description = "Altera o status do entregador para disponível para novas entregas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status alterado para DISPONÍVEL"),
                    @ApiResponse(responseCode = "404", description = "Entregador não encontrado")
            }
    )
    @PatchMapping("/{id}/disponivel")
    public ResponseEntity<Void> marcarComoDisponivel(@PathVariable Long id) {
        entregadorService.marcarComoDisponivel(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Marcar como Indisponível",
            description = "Altera o status do entregador para indisponível.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status alterado para INDISPONÍVEL"),
                    @ApiResponse(responseCode = "404", description = "Entregador não encontrado")
            }
    )
    @PatchMapping("/{id}/indisponivel")
    public ResponseEntity<Void> marcarComoIndisponivel(@PathVariable Long id) {
        entregadorService.marcarComoIndisponivel(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Atribuir Entrega",
            description = "Vincula uma entrega PENDENTE a um entregador disponível.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entrega atribuída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entregador ou Entrega não encontrados"),
                    @ApiResponse(responseCode = "409", description = "Conflito: Entregador indisponível ou Entrega já iniciada/concluída")
            }
    )
    @PatchMapping("/{idEntregador}/atribuir/{idEntrega}")
    public ResponseEntity<Void> atribuirEntrega(@PathVariable Long idEntregador, @PathVariable Long idEntrega) {
        entregadorService.atribuirEntrega(idEntregador, idEntrega);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Iniciar Entrega",
            description = "Muda o status da entrega para EM_ROTA.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entrega iniciada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "409", description = "Conflito: Entrega não está no status PENDENTE")
            }
    )
    @PatchMapping("/entrega/{idEntrega}/iniciar")
    public ResponseEntity<Void> iniciarEntrega(@PathVariable Long idEntrega) {
        entregadorService.iniciarEntrega(idEntrega);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Finalizar Entrega",
            description = "Muda o status da entrega para CONCLUIDA.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entrega finalizada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "409", description = "Conflito: Entrega não está no status EM_ROTA")
            }
    )
    @PatchMapping("/entrega/{idEntrega}/finalizar")
    public ResponseEntity<Void> finalizarEntrega(@PathVariable Long idEntrega) {
        entregadorService.finalizarEntrega(idEntrega);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Cancelar Entrega",
            description = "Muda o status da entrega para CANCELADA.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Entrega cancelada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
                    @ApiResponse(responseCode = "409", description = "Conflito: Entrega já concluída ou cancelada")
            }
    )
    @PatchMapping("/entrega/{idEntrega}/cancelar")
    public ResponseEntity<Void> cancelarEntrega(@PathVariable Long idEntrega) {
        entregadorService.cancelarEntrega(idEntrega);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Solicitar Recuperação",
            description = "Inicia o processo de recuperação de senha, enviando o token por e-mail.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email de recuperação enviado (ou mensagem genérica de sucesso)"),
                    @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido")
            }
    )
    @PostMapping("/recuperacao/iniciar")
    public ResponseEntity<Map<String, String>> iniciarRecuperacaoSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "O campo 'email' é obrigatório."));
        }

        entregadorService.iniciarRecuperacaoSenha(email);
        return ResponseEntity.ok(Map.of("message", "Se o email estiver cadastrado, um link de recuperação será enviado."));
    }

    @Operation(
            summary = "Redefinir Senha",
            description = "Recebe o token e a nova senha no corpo da requisição para redefinir a senha.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Token inválido, expirado, ou nova senha com formato inválido")
            }
    )
    @PostMapping("/recuperacao/redefinir")
    public ResponseEntity<Map<String, String>> redefinirSenha(@RequestBody @Valid RedefinirSenhaRequestDTO requestDTO) {
        entregadorService.redefinirSenha(requestDTO.token(), requestDTO.novaSenha());
        return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso."));
    }
}