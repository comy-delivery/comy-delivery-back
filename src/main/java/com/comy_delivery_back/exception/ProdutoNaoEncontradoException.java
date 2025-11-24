package com.comy_delivery_back.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Long id) {
      super(String.format("O produto com o id %d não foi encontrado", id));
    }
}
