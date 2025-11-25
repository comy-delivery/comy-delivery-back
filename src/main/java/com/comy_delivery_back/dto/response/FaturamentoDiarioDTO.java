package com.comy_delivery_back.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturamentoDiarioDTO(
        LocalDate data,
        BigDecimal faturamentoTotal
) {


}
