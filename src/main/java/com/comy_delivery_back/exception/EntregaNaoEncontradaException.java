package com.comy_delivery_back.exception;

public class EntregaNaoEncontradaException extends RuntimeException {
    public EntregaNaoEncontradaException(Long id) {
        super(String.format("A entrega com o id %d não foi encontrada", id));
    }
}
