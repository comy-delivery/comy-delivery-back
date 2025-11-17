package com.comy_delivery_back.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusPedido {

    PENDENTE("Pendente"),
    CONFIRMADO("Confirmado"),
    EM_PREPARO("Em preparo"),
    PRONTO("Pronto"),
    SAIU_PARA_ENTREGA("Saiu para entrega"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado"),;

    private final String description;

}
