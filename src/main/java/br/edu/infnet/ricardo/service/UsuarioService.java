package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Usuario;
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

    public void salvar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<Usuario> todos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(long id) {
        return usuarioRepository.findById(id);
    }
}
