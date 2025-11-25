package com.comy_delivery_back.exception;

public class CupomNaoEncontradoException extends RuntimeException {

    public CupomNaoEncontradoException(Long id) {

      super(String.format("O cupom com o id %d não foi encontrado", id));
    }

  public CupomNaoEncontradoException(String codigo) {

    super(String.format("O cupom com o código %s não foi encontrado", codigo));
  }


}
