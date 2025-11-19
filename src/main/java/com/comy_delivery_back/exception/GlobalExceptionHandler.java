package com.comy_delivery_back.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(SignatureVerificationException.class)
//    public ResponseEntity<ProblemDetail> handleSignatureVerificationException(SignatureVerificationException exception) {
//        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

//    @ExceptionHandler(InvalidJwtAuthenticationException.class)
//    public ResponseEntity<ProblemDetail> handleInvalidJwtAuthenticationException(InvalidJwtAuthenticationException exception) {
//        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

//    @ExceptionHandler(JWTDecodeException.class)
//    public ResponseEntity<ProblemDetail> handleJWTDecodeException(JWTDecodeException exception) {
//        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

//    @ExceptionHandler(UsuarioNaoEncontradoException.class)
//    public ResponseEntity<ProblemDetail> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException exception){
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

//    @ExceptionHandler(EntregadorNaoEncontradoException.class)
//    public ResponseEntity<ProblemDetail> handleEntregadorNaoEncontradoException(EntregadorNaoEncontradoException exception){
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

//    @ExceptionHandler(ClienteNaoEncontradoException.class)
//    public ResponseEntity<ProblemDetail> handleClienteNaoEncontradoException(ClienteNaoEncontradoException exception){
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

//    @ExceptionHandler(RestauranteNaoEncontradoException.class)
//    public ResponseEntity<ProblemDetail> handleRestauranteNaoEncontradoException(RestauranteNaoEncontradoException exception){
//        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
//        problemDetail.setTitle(httpStatus.name());
//
//        return ResponseEntity.status(httpStatus).body(problemDetail);
//    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException exception) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(AvaliacaoNaoEncontradaException.class)
    public ResponseEntity<ProblemDetail> handleAvaliacaoNaoEncontradaException(AvaliacaoNaoEncontradaException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(AdicionalNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleAdicionalNaoEncontradoException(AdicionalNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(CepNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleCepNaoEncontradoException(CepNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(CupomNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleCupomNaoEncontradoException(CupomNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleEnderecoNaoEncontradoException(EnderecoNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(PedidoException.class)
    public ResponseEntity<ProblemDetail> handlePedidoException(PedidoException exception){
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handlePedidoNaoEncontradoException(PedidoNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleProdutoNaoEncontradoException(ProdutoNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ProblemDetail> handleFeignException(FeignException exception){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ProblemDetail> handleSQLException(SQLException exception){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

}
