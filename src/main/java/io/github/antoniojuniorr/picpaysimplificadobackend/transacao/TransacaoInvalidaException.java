package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

public class TransacaoInvalidaException extends RuntimeException {
    public TransacaoInvalidaException(String message) {
        super(message);
    }
}
