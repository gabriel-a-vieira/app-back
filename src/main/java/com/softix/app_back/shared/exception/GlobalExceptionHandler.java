package com.softix.app_back.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {

        ErrorResponse body = new ErrorResponse(ex.getStatus().value(), ex.getStatus().getReasonPhrase(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(ex.getStatus()).body(body);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {

        log.error("Violacao de integridade de dados em {}", request.getRequestURI(), ex);

        String message = "Nao foi possivel salvar os dados informados. Verifique se algum campo, como um link de imagem, esta em um formato invalido ou muito grande.";

        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {

        log.error("Erro inesperado em {}", request.getRequestURI(), ex);

        ErrorResponse body = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Ocorreu um erro inesperado. Tente novamente em instantes.", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);

    }

}
