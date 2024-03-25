package io.github.antoniojuniorr.picpaysimplificadobackend.autorizacao;

public class TransacaoNaoAutorizadaException extends RuntimeException {
    public TransacaoNaoAutorizadaException(String message) {
        super(message);
    }
}
