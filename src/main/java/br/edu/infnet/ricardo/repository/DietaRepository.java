package br.edu.infnet.ricardo.repository;

import br.edu.infnet.ricardo.domain.Dieta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DietaRepository extends JpaRepository<Dieta, Long> {
    Optional<Dieta> findByUsuarioId(Long usuarioId);
}
