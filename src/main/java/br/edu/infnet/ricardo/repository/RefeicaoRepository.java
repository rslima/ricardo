package br.edu.infnet.ricardo.repository;

import br.edu.infnet.ricardo.domain.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByDietaUsuarioId(Long usuarioId);

    List<Refeicao> findByDietaUsuarioIdAndData(Long usuarioId, LocalDate data);

    List<Refeicao> findByDietaUsuarioIdAndDataBefore(Long usuarioId, LocalDate max);

    List<Refeicao> findByDietaUsuarioIdAndDataBetween(Long usuarioId, LocalDate min, LocalDate max);

    List<Refeicao> findByDietaUsuarioIdAndDataGreaterThanEqual(Long usuarioId, LocalDate min);
}
