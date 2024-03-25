package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("TRANSACAO")
public record Transacao(
        @Id Long id,
        Long pagador,
        Long recebedor,
        BigDecimal valor,
        @CreatedDate LocalDateTime criadoEm) {

    public Transacao {
        valor = valor.setScale(2);
    }
}
