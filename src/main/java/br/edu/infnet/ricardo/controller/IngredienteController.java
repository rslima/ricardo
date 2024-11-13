package br.edu.infnet.ricardo.controller;

import br.edu.infnet.ricardo.domain.Ingrediente;
import br.edu.infnet.ricardo.service.IngredienteNotFoundException;
import br.edu.infnet.ricardo.service.IngredienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/ingredientes")
@Slf4j
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listar() {
        return ResponseEntity.ok(ingredienteService.todos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> buscarPorId(@PathVariable Long id) {
        return ingredienteService.obtemPorId(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Ingrediente não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Ingrediente> salvar(@RequestBody @Valid Ingrediente ingrediente) {
        ingrediente.setId(null);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ingredienteService.salvar(ingrediente));
        } catch (IngredienteNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> atualizar(
            @PathVariable long id,
            @RequestBody @Valid Ingrediente ingrediente) {

        ingrediente.setId(id);

        try {
            return ResponseEntity.ok(ingredienteService.salvar(ingrediente));
        } catch (IngredienteNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {

        try {
            ingredienteService.deletarPorId(id);
        } catch (IngredienteNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.noContent().build();

    }

}
