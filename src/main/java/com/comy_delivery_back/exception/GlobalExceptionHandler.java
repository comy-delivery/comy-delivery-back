package com.comy_delivery_back.exception;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.stream.Collectors;

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


    @ExceptionHandler(EntregadorNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleEntregadorNaoEncontradoException(EntregadorNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleClienteNaoEncontradoException(ClienteNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(RestauranteNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleRestauranteNaoEncontradoException(RestauranteNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

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

    @ExceptionHandler(CupomInvalidoException.class)
    public ResponseEntity<ProblemDetail> handleCupomInvalidoException(CupomInvalidoException exception){
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
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

    @ExceptionHandler(RegistrosDuplicadosException.class)
    public ResponseEntity<ProblemDetail> handleRegistroDuplicadoException(RegistrosDuplicadosException exception){
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ProblemDetail> handleRegraDeNegocioException(RegraDeNegocioException exception){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(ItemPedidoNaoEncontradoException.class)
    public ResponseEntity<ProblemDetail> handleItemPedidoNaoEncontradoException(ItemPedidoNaoEncontradoException exception){
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage() );
        problemDetail.setTitle(httpStatus.name());

        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> handleRuntimeException(RuntimeException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                httpStatus, "Erro inesperado no servidor");
        problemDetail.setTitle(httpStatus.name());
        return ResponseEntity.status(httpStatus).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(
            MethodArgumentNotValidException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                httpStatus,
                "Validação falhou"
        );
        detail.setProperty("errors",
                ex.getBindingResult().getFieldErrors().stream()
                        .map(e -> e.getField() + ": " + e.getDefaultMessage())
                        .collect(Collectors.toList())
        );
        return ResponseEntity.status(httpStatus).body(detail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(
            EntityNotFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                httpStatus,
                ex.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(detail);
    }



}
