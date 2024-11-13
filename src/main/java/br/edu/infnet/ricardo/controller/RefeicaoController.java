package br.edu.infnet.ricardo.controller;


import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.service.IngredienteNotFoundException;
import br.edu.infnet.ricardo.service.RefeicaoDTO;
import br.edu.infnet.ricardo.service.RefeicaoService;
import br.edu.infnet.ricardo.service.UsuarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/dietas")
@RequiredArgsConstructor
@Slf4j
public class RefeicaoController {
    private final RefeicaoService refeicaoService;

    @GetMapping("/{usuarioId}/refeicoes")
    public ResponseEntity<List<Refeicao>> buscarRefeicoes(
            @PathVariable Long usuarioId,
            @RequestParam Optional<LocalDate> data,
            @RequestParam Optional<LocalDate> minData,
            @RequestParam Optional<LocalDate> maxData) {

        try {
            if (data.isPresent()) {
                var dt = data.get();
                return ResponseEntity.ok(refeicaoService.buscaPorData(usuarioId, dt));
            }

            if (minData.isPresent() || maxData.isPresent()) {
                if (minData.isPresent() && maxData.isPresent()) {
                    var min = minData.get();
                    var max = maxData.get();

                    if (min.isAfter(max)) {
                        throw new ResponseStatusException(BAD_REQUEST, "Data inicial maior que a data final");
                    }

                    return ResponseEntity.ok(refeicaoService.buscaPorIntervaloDeDatas(usuarioId, min, max));
                } else if (minData.isPresent()) {
                    var min = minData.get();

                    return ResponseEntity.ok(refeicaoService.buscaPorDataInicial(usuarioId, min));
                } else {
                    var max = maxData.get();

                    return ResponseEntity.ok(refeicaoService.buscaPorDataFinal(usuarioId, max));
                }
            }

            return ResponseEntity.ok(refeicaoService.listarRefeicoes(usuarioId));

        } catch (UsuarioNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/{usuarioId}/refeicoes/{refeicaoId}")
    public ResponseEntity<Refeicao> buscarRefeicaoPorId(
            @PathVariable Long usuarioId,
            @PathVariable Long refeicaoId) {
        try {
            return refeicaoService.buscaPorId(usuarioId, refeicaoId).map(ResponseEntity::ok).orElseThrow(
                    () -> new ResponseStatusException(NOT_FOUND, "Refeição com id " + refeicaoId + "não encontrada."));
        } catch (UsuarioNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{usuarioId}/refeicoes")
    public ResponseEntity<Refeicao> adicionarRefeicao(
            @PathVariable Long usuarioId,
            @RequestBody RefeicaoDTO refeicaoDTO) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(refeicaoService.salva(usuarioId, refeicaoDTO));
        } catch (UsuarioNotFoundException | IngredienteNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }

    }

    @PutMapping("/{usuarioId}/refeicoes/{refeicaoId}")
    public ResponseEntity<Refeicao> atualizarRefeicao(
            @PathVariable Long usuarioId,
            @PathVariable Long refeicaoId,
            @RequestBody RefeicaoDTO refeicaoDTO) {
        try {
            refeicaoDTO.setId(refeicaoId);
            return ResponseEntity.ok(refeicaoService.salva(usuarioId, refeicaoDTO));
        } catch (UsuarioNotFoundException | IngredienteNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{usuarioId}/refeicoes/{refeicaoId}")
    public ResponseEntity<Void> excluiRefeicao(
            @PathVariable Long usuarioId,
            @PathVariable Long refeicaoId) {
        try {
            refeicaoService.excluiById(usuarioId, refeicaoId);
            return ResponseEntity.noContent().build();

        } catch (UsuarioNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, e.getMessage());
        }
    }


}
