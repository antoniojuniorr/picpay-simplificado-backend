package io.github.antoniojuniorr.picpaysimplificadobackend.transacao;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends ListCrudRepository<Transacao, Long> {
}
