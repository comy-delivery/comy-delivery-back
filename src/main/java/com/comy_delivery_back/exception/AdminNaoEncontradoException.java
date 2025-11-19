package com.comy_delivery_back.exception;

public class AdminNaoEncontradoException extends RuntimeException {
    public AdminNaoEncontradoException(Long id) {
        super(String.format("O admin com o id %d não foi encontrado", id));
    }
}
