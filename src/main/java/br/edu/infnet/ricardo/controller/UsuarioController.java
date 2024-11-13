package br.edu.infnet.ricardo.controller;

import br.edu.infnet.ricardo.domain.Usuario;
import br.edu.infnet.ricardo.service.TrocaSenhaDTO;
import br.edu.infnet.ricardo.service.UsuarioNotFoundException;
import br.edu.infnet.ricardo.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> todos() {
        return ResponseEntity.ok(usuarioService.todos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable long id) {
        return usuarioService.buscarPorId(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody @Valid Usuario usuario) {
        usuario.setId(null);
        try {
            return ResponseEntity.status(CREATED).body(usuarioService.salvar(usuario));
        } catch (UsuarioNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable long id,
            @RequestBody @Valid Usuario usuario) {
        usuario.setId(id);
        try {
            return ResponseEntity.ok(usuarioService.salvar(usuario));
        } catch (UsuarioNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deletar(@PathVariable long id) {

        try {
            usuarioService.deletaPorId(id);
        } catch (UsuarioNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<Usuario> atualizarSenha(@PathVariable long id, @RequestBody @Valid TrocaSenhaDTO senhaDTO) {
         senhaDTO.setId(id);
         try {
             return ResponseEntity.ok(usuarioService.trocaSenha(senhaDTO));
         } catch (UsuarioNotFoundException e) {
             throw new ResponseStatusException(NOT_FOUND, e.getMessage());
         }
    }

}
