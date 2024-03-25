package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

public enum TipoTransacao {
    COMUM(1), LOJISTA(2);

    private int valor;

    TipoTransacao(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }
}
