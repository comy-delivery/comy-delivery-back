package com.comy_delivery_back.dto.response;

import com.comy_delivery_back.enums.VeiculoEntregador;
import com.comy_delivery_back.model.Entrega;
import com.comy_delivery_back.model.Entregador;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Resposta contendo dados de um entregador")
public record EntregadorResponseDTO(
        @Schema(description = "ID do entregador", example = "1")
        Long id,

        @Schema(description = "Nome de usuário", example = "motoboy_carlos")
        String username,

        @Schema(description = "Nome completo", example = "Carlos Motoboy")
        String nmEntregador,

        @Schema(description = "Email", example = "carlos@moto.com")
        String emailEntregador,

        @Schema(description = "CPF", example = "55544433322")
        String cpfEntregador,

        @Schema(description = "Telefone", example = "11977776666")
        String telefoneEntregador,

        @Schema(description = "Tipo de veículo", example = "MOTO")
        VeiculoEntregador veiculo,

        @Schema(description = "Placa do veículo", example = "ABC1234")
        String placa,

        @Schema(description = "Data de cadastro", example = "2024-01-03")
        LocalDate dataCadastroEntregador,

        @Schema(description = "IDs das entregas realizadas", example = "[1, 2, 3]")
        List<Long> entregaId,

        @Schema(description = "Status de ativo", example = "true")
        boolean isAtivo
) {
    public EntregadorResponseDTO(Entregador entregador){
        this(
                entregador.getId(),
                entregador.getUsername(),
                entregador.getNmEntregador(),
                entregador.getEmailEntregador(),
                entregador.getCpfEntregador(),
                entregador.getTelefoneEntregador(),
                entregador.getVeiculo(),
                entregador.getPlaca(),
                entregador.getDataCadastroEntregador(),
                entregador.getEntregasEntregador() != null
                        ? entregador.getEntregasEntregador().stream().map(Entrega::getIdEntrega).toList()
                        : List.of(),
                entregador.isAtivo()
        );
    }
}
