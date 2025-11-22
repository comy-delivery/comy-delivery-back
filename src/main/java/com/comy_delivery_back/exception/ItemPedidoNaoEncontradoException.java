package com.comy_delivery_back.exception;

public class ItemPedidoNaoEncontradoException extends RuntimeException {
    public ItemPedidoNaoEncontradoException(Long id) {
        super(String.format("Item de pedido com id %d não foi encontrado", id));
    }
}
