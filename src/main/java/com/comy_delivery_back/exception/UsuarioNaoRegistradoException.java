package com.comy_delivery_back.exception;

public class UsuarioNaoRegistradoException extends RuntimeException {
    public UsuarioNaoRegistradoException(String email) {
        super(String.format("Usuario não encontrado, registre manualmente, email: %s", email));
    }
}
