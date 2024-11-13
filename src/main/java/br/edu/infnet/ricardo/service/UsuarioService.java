package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Dieta;
import br.edu.infnet.ricardo.domain.Usuario;
import br.edu.infnet.ricardo.repository.DietaRepository;
import br.edu.infnet.ricardo.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final DietaRepository dietaRepository;

    public Usuario salvar(Usuario usuario) {
        final var u = usuarioRepository.save(usuario);

        if (usuario.getId() == null) {
            final var d = new Dieta();
            d.setUsuario(u);
            d.setId(u.getId());

            dietaRepository.save(d);
        }

        return u;
    }

    public List<Usuario> todos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(long id) {
        return usuarioRepository.findById(id);
    }
}
