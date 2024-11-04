package br.edu.infnet.ricardo.repository;

import br.edu.infnet.ricardo.domain.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}
