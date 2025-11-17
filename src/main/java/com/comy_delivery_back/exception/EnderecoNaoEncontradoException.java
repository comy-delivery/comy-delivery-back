package com.comy_delivery_back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnderecoNaoEncontradoException extends RuntimeException {

    public EnderecoNaoEncontradoException(Long idEndereco) {
        super(String.format("O endereco com o código %d não foi encontrado", idEndereco));
    }
}
