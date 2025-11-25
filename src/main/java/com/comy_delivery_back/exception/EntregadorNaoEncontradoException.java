package com.comy_delivery_back.exception;

public class EntregadorNaoEncontradoException extends RuntimeException {
    public EntregadorNaoEncontradoException(Long id) {
        super(String.format("O entregador com o id %d não foi encontrado", id));
    }


    public EntregadorNaoEncontradoException(String identificador){
        super(String.format("O restaurante com o identificador %s não foi encontrado"));
    }
}
