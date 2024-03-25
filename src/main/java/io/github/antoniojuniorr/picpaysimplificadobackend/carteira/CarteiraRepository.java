package io.github.antoniojuniorr.picpaysimplificadobackend.carteira;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends CrudRepository<Carteira, Long> {
}
