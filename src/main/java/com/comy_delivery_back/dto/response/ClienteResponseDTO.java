package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.model.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Resposta contendo dados de um cliente")
public record ClienteResponseDTO(
        @Schema(description = "ID do cliente", example = "1")
        Long id,

        @Schema(description = "Nome de usuário", example = "cliente_joao")
        String username,

        @Schema(description = "Nome completo", example = "João da Silva")
        String nmCliente,

        @Schema(description = "Email", example = "joao@email.com")
        String emailCliente,

        @Schema(description = "CPF", example = "99988877766")
        String cpfCliente,

        @Schema(description = "Telefone", example = "11988887777")
        String telefoneCliente,

        @Schema(description = "Data de cadastro", example = "2024-01-02")
        LocalDate dataCadastroCliente,

        @Schema(description = "Lista de endereços do cliente")
        List<EnderecoResponseDTO> enderecos,

        @Schema(description = "Status de ativo", example = "true")
        boolean isAtivo
) {
    public ClienteResponseDTO(Cliente cliente){
        this(
                cliente.getId(),
                cliente.getUsername(),
                cliente.getNmCliente(),
                cliente.getEmailCliente(),
                cliente.getCpfCliente(),
                cliente.getTelefoneCliente(),
                cliente.getDataCadastroCliente(),
                cliente.getEnderecos().stream().map(EnderecoResponseDTO::new).toList(),
                cliente.isAtivo()
        );
    }
}

