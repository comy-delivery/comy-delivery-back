package com.comy_delivery_back.exception;

public class RestauranteNaoEncontradoException extends RuntimeException {
    public RestauranteNaoEncontradoException(Long id) {
        super(String.format("O restaurante com o id %d não foi encontrado", id));
    }

    public RestauranteNaoEncontradoException(String identificador){
        super(String.format("O restaurante com o identificador %s não foi encontrado"));
    }
}
