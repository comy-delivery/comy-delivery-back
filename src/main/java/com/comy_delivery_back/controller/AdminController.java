package com.comy_delivery_back.controller;

import com.comy_delivery_back.dto.request.AdminRequestDTO;
import com.comy_delivery_back.dto.response.AdminResponseDTO;
import com.comy_delivery_back.service.AdminService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequestMapping("/admins")
@Tag(name = "Administradores", description = "Controller para as operações com o banco de dados para os admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo administrador",
            description = "Cria um novo registro de administrador. Retorna o objeto criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin cadastrado com sucesso (CREATED)."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (DTO malformado ou campos faltando)."),
            @ApiResponse(responseCode = "409", description = "Conflito: CPF ou Email já cadastrado (CONFLICT).")
    })
    public ResponseEntity<AdminResponseDTO> cadastrarAdmin(
            @RequestBody @Valid AdminRequestDTO adminRequestDTO) {

        AdminResponseDTO novoAdmin = adminService.cadastrarAdmin(adminRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoAdmin);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um administrador por ID",
            description = "Retorna os detalhes de um administrador específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Admin encontrado e retornado (OK)."),
            @ApiResponse(responseCode = "404", description = "Admin não encontrado para o ID informado (NOT FOUND).")
    })
    public ResponseEntity<AdminResponseDTO> buscarAdminPorId(
            @Parameter(description = "ID do administrador a ser buscado", example = "1")
            @PathVariable Long id) {

        AdminResponseDTO admin = adminService.buscarAdminPorId(id);

        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativa um administrador por ID",
            description = "Realiza um soft delete, alterando o status 'ativo' para falso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Admin desativado com sucesso (NO CONTENT)."),
            @ApiResponse(responseCode = "404", description = "Admin não encontrado para o ID informado (NOT FOUND).")
    })
    public ResponseEntity<Void> deletarAdmin(
            @Parameter(description = "ID do administrador a ser desativado", example = "1")
            @PathVariable Long id) {

        adminService.deletarAdmin(id);

        return ResponseEntity.noContent().build();
    }
}