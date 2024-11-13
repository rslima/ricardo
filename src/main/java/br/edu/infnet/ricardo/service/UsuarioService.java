package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Dieta;
import br.edu.infnet.ricardo.domain.Usuario;
import br.edu.infnet.ricardo.repository.DietaRepository;
import br.edu.infnet.ricardo.repository.UsuarioRepository;
import jakarta.validation.Valid;
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

    public Usuario salvar(Usuario usuario) throws UsuarioNotFoundException {

        Optional<Usuario> usuarioExistente = Optional.empty();


        if (usuario.getId() != null) {
            usuarioExistente = usuarioRepository.findById(usuario.getId());
        }

        if (usuarioExistente.isPresent() || usuario.getId() == null) {

            final var u = usuarioRepository.save(usuario);

            if (usuario.getId() == null) {
                final var d = new Dieta();
                d.setUsuario(u);
                d.setId(u.getId());

                dietaRepository.save(d);
            }

            return u;
        }

        throw new UsuarioNotFoundException(usuario.getId());

    }

    public List<Usuario> todos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(long id) {
        return usuarioRepository.findById(id);
    }

    public void deletaPorId(long id) throws UsuarioNotFoundException {
        final var u = buscarPorId(id);

        if (u.isPresent()) {
            usuarioRepository.delete(u.get());
        } else {
            throw new UsuarioNotFoundException(id);
        }


    }

    public Usuario trocaSenha(@Valid TrocaSenhaDTO senhaDTO) throws UsuarioNotFoundException {

        final var usuario = buscarPorId(senhaDTO.getId())
                .orElseThrow(() -> new UsuarioNotFoundException(senhaDTO.getId()));

        usuario.setSenha(senhaDTO.getSenha());

        return usuarioRepository.save(usuario);
    }
}
