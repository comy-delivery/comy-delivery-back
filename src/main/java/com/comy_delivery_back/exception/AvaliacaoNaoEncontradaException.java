package com.comy_delivery_back.exception;

public class AvaliacaoNaoEncontradaException extends RuntimeException {

    public AvaliacaoNaoEncontradaException(Long id) {
        super(String.format("A avaliação com o código %d não foi encontrada", id));
    }
}
