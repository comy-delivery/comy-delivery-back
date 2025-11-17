package com.comy_delivery_back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CepNaoEncontradoException extends RuntimeException {
    public CepNaoEncontradoException(String cep) {
        super("O cep informado não foi encontrado "+cep);
    }
}
