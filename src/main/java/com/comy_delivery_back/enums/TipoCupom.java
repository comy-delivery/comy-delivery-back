package com.comy_delivery_back.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCupom {
    VALOR_FIXO("Valor fixo"),
    PERCENTUAL("Percentual"),;

    private final String description;

}
