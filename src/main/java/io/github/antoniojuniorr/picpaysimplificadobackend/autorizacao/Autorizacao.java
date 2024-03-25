package io.github.antoniojuniorr.picpaysimplificadobackend.autorizacao;

public record Autorizacao(
        String message
) {
    public boolean isAutorizado() {
        return message.equals("Autorizado");
    }
}
