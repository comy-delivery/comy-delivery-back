package com.comy_delivery_back.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormaPagamento {
    CREDITO("CRÉDITO"),
    DEBITO("DÉBITO"),
    PIX("PIX"),
    DINHEIRO("DINHEIRO"),;

    private final String description;
}
