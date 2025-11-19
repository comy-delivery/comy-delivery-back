package com.comy_delivery_back.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(Long id) {
        super(String.format("O cliente com o id %d não foi encontrado", id));
    }

    public ClienteNaoEncontradoException(String identificador){
        super(String.format("O cliente com o identificador %s não foi encontrado"));
    }
}
