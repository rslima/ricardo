package br.edu.infnet.ricardo.controller;

import br.edu.infnet.ricardo.domain.Ingrediente;
import br.edu.infnet.ricardo.service.IngredienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredientes")
@Slf4j
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteService ingredienteService;

    @GetMapping
    public List<Ingrediente> listar() {
        return ingredienteService.todos();
    }
}
