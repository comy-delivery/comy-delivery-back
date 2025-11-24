package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AtualizarClienteRequestDTO;
import com.comy_delivery_back.dto.request.ClienteRequestDTO;
import com.comy_delivery_back.dto.request.EnderecoRequestDTO;
import com.comy_delivery_back.dto.response.ClienteResponseDTO;
import com.comy_delivery_back.dto.response.EnderecoResponseDTO;
import com.comy_delivery_back.dto.response.PedidoResponseDTO;
import com.comy_delivery_back.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clientes", description = "Controller para as operações com o banco de dados para os produtos")
@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @Operation(summary = "Cadastrar Novo Cliente",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "CPF/Email já cadastrado")
            })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO requestDTO) {
        ClienteResponseDTO response = clienteService.cadastrarCliente(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar Cliente por ID",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            })
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long idCliente) {
        ClienteResponseDTO response = clienteService.buscarClientePorId(idCliente);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Clientes Ativos")
    @GetMapping("/ativos")
    public ResponseEntity<List<ClienteResponseDTO>> buscarClientesAtivos() {
        List<ClienteResponseDTO> response = clienteService.buscarClientesAtivos();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar Dados do Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ClienteResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
                    @ApiResponse(responseCode = "409", description = "Email já em uso")
            })
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDTO> atualizarDadosCliente(@PathVariable Long idCliente,
                                                                    @RequestBody AtualizarClienteRequestDTO requestDTO) {
        ClienteResponseDTO response = clienteService.atualizarDadosCliente(idCliente, requestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Desativar Cliente (Soft Delete)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            })
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long idCliente) {
        clienteService.deletarCliente(idCliente);
        return ResponseEntity.noContent().build();
    }

    // --- Métodos para Endereços ---

    @Operation(summary = "Listar Endereços do Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            })
    @GetMapping("/{idCliente}/enderecos")
    public ResponseEntity<List<EnderecoResponseDTO>> listarEnderecosDoCliente(@PathVariable Long idCliente) {
        List<EnderecoResponseDTO> response = clienteService.listarEnderecosDoCliente(idCliente);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Adicionar Novo Endereço",
            responses = {
                    @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            })
    @PostMapping("/{idCliente}/enderecos")
    public ResponseEntity<EnderecoResponseDTO> cadastrarNovoEndereco(@PathVariable Long idCliente,
                                                                     @RequestBody @Valid EnderecoRequestDTO requestDTO) {
        EnderecoResponseDTO response = clienteService.cadastrarNovoEndereco(idCliente, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar Endereço Específico",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente/Endereço não encontrado")
            })
    @PutMapping("/{idCliente}/enderecos/{idEndereco}")
    public ResponseEntity<EnderecoResponseDTO> atualizarEnderecoCliente(@PathVariable Long idCliente,
                                                                        @PathVariable Long idEndereco,
                                                                        @RequestBody EnderecoRequestDTO requestDTO) {
        EnderecoResponseDTO response = clienteService.atualizarEnderecoCliente(idCliente, idEndereco, requestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar Pedidos do Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PedidoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            })
    @GetMapping("/{idCliente}/pedidos")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos(@PathVariable Long idCliente) {
        List<PedidoResponseDTO> response = clienteService.listarPedidos(idCliente);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Iniciar Recuperação de Senha",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email de recuperação enviado (ou mensagem genérica de sucesso)")
            })
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> iniciarRecuperacaoSenha(@RequestParam String email) {
        clienteService.iniciarRecuperacaoSenha(email);
        return ResponseEntity.ok("Se o email estiver cadastrado, um link de recuperação será enviado.");
    }

    @Operation(summary = "Redefinir Senha",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Token inválido ou expirado")
            })
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestParam String token,
                                                 @RequestParam String novaSenha) {
        clienteService.redefinirSenha(token, novaSenha);
        return ResponseEntity.ok("Senha redefinida com sucesso.");
    }
}