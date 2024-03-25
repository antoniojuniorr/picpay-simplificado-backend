package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransacaoExceptionHandler {

    @ExceptionHandler(TransacaoInvalidaException.class)
    public ResponseEntity<Object> handle(TransacaoInvalidaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
