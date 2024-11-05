package br.edu.infnet.ricardo.repository;

import br.edu.infnet.ricardo.domain.IngredientePorcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientePorcaoRepository extends JpaRepository<IngredientePorcao, Long> {
}
