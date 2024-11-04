package br.edu.infnet.ricardo.repository;

import br.edu.infnet.ricardo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
