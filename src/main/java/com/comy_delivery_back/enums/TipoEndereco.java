package com.comy_delivery_back.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoEndereco {
    CASA("casa"),
    TRABALHO("trabalho"),
    OUTRO("outro"),
    MATRIZ("matriz"),
    FILIAL("filial");

    private final String description;
}
