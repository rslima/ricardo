package br.edu.infnet.ricardo.controller;

import br.edu.infnet.ricardo.domain.Dieta;
import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.service.DietaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/api/v1/dietas")
@Slf4j
@RequiredArgsConstructor
public class DietaController {

    private final DietaService dietaService;

    @GetMapping
    public List<Dieta> listar() {
        return dietaService.todos();
    }

    @GetMapping("/{usuarioId}")
    public Dieta buscarPorId(@PathVariable Long usuarioId) {
        return dietaService.buscaPorIdUsuario(usuarioId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/{usuarioId}/datas/{data}")
    public List<Refeicao> buscarPorData(@PathVariable Long usuarioId, @PathVariable LocalDate data) {
        return dietaService.buscaPorData(usuarioId, data);
    }

}