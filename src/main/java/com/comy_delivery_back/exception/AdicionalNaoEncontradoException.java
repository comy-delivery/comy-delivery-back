package com.comy_delivery_back.exception;

public class AdicionalNaoEncontradoException extends RuntimeException {
    public AdicionalNaoEncontradoException(Long id) {
      super(String.format("O adicional com o id %d não foi encontrado", id));
    }
}
