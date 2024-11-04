package br.edu.infnet.ricardo.controller;

import br.edu.infnet.ricardo.domain.Usuario;
import br.edu.infnet.ricardo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> todos() {
        return usuarioService.todos();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable long id) {
        return usuarioService.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
    }


}
