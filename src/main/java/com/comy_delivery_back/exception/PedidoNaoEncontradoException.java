package com.comy_delivery_back.exception;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(Long id) {
      super(String.format("O pedido com o id %d não foi encontrado", id));
    }
}
