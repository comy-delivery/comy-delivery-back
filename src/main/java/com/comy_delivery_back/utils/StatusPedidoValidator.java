package com.comy_delivery_back.utils;

import com.comy_delivery_back.enums.StatusPedido;
import com.comy_delivery_back.exception.PedidoException;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class StatusPedidoValidator {


    private static final Map<StatusPedido, Set<StatusPedido>> TRANSICOES_PERMITIDAS;

    static {
        TRANSICOES_PERMITIDAS = new EnumMap<>(StatusPedido.class);

        TRANSICOES_PERMITIDAS.put(StatusPedido.PENDENTE,
                EnumSet.of(StatusPedido.CONFIRMADO, StatusPedido.CANCELADO));

        TRANSICOES_PERMITIDAS.put(StatusPedido.CONFIRMADO,
                EnumSet.of(StatusPedido.EM_PREPARO, StatusPedido.CANCELADO));

        TRANSICOES_PERMITIDAS.put(StatusPedido.EM_PREPARO,
                EnumSet.of(StatusPedido.PRONTO, StatusPedido.CANCELADO));

        TRANSICOES_PERMITIDAS.put(StatusPedido.PRONTO,
                EnumSet.of(StatusPedido.SAIU_PARA_ENTREGA, StatusPedido.CANCELADO));

        TRANSICOES_PERMITIDAS.put(StatusPedido.SAIU_PARA_ENTREGA,
                EnumSet.of(StatusPedido.ENTREGUE, StatusPedido.CANCELADO));

        TRANSICOES_PERMITIDAS.put(StatusPedido.ENTREGUE,
                EnumSet.noneOf(StatusPedido.class));

        TRANSICOES_PERMITIDAS.put(StatusPedido.CANCELADO,
                EnumSet.noneOf(StatusPedido.class));
    }


    public static void validarTransicao(StatusPedido statusAtual, StatusPedido novoStatus) {
        if (statusAtual == null || novoStatus == null) {
            throw new PedidoException("Status não pode ser nulo");
        }

        if (statusAtual == novoStatus) {
            throw new PedidoException("Pedido já está no status: " + statusAtual.getDescription());
        }

        Set<StatusPedido> permitidos = TRANSICOES_PERMITIDAS.get(statusAtual);

        if (permitidos == null || !permitidos.contains(novoStatus)) {
            throw new PedidoException(
                    String.format("Transição não permitida: %s → %s",
                            statusAtual.getDescription(),
                            novoStatus.getDescription())
            );
        }
    }

    public static boolean podeModificar(StatusPedido status) {
        return status == StatusPedido.PENDENTE || status == StatusPedido.CONFIRMADO;
    }

}
