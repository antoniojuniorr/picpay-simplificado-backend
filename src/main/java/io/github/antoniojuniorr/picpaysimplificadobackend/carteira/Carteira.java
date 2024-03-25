package io.github.antoniojuniorr.picpaysimplificadobackend.carteira;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("CARTEIRA")
public record Carteira(
        @Id Long id,
        String nomeCompleto,
        Long cpf,
        String email,
        String senha,
        int tipo,
        BigDecimal saldo,
        @Version Long versao) {

    public Carteira debitar(BigDecimal valor) {
        return new Carteira(id, nomeCompleto, cpf, email, senha, tipo, saldo.subtract(valor), versao);
    }

    public Carteira credito(BigDecimal valor) {
        return new Carteira(id, nomeCompleto, cpf, email, senha, tipo, saldo.add(valor), versao);
    }
}
