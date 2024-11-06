package br.edu.infnet.ricardo.controller;


import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.service.RefeicaoService;
import br.edu.infnet.ricardo.service.UsuarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/dietas")
@RequiredArgsConstructor
@Slf4j
public class RefeicaoController {
    private final RefeicaoService refeicaoService;

    @GetMapping("/{usuarioId}/refeicoes")
    public List<Refeicao> buscarRefeicoes(
            @PathVariable Long usuarioId,
            @RequestParam Optional<LocalDate> data,
            @RequestParam Optional<LocalDate> minData,
            @RequestParam Optional<LocalDate> maxData) {

        try {
            if (data.isPresent()) {
                var dt = data.get();
                return refeicaoService.buscaPorData(usuarioId, dt);
            }

            if (minData.isPresent() || maxData.isPresent()) {
                if (minData.isPresent() && maxData.isPresent()) {
                    var min = minData.get();
                    var max = maxData.get();

                    if (min.isAfter(max)) {
                        throw new ResponseStatusException(BAD_REQUEST, "Data inicial maior que a data final");
                    }

                    return refeicaoService.buscaPorIntervaloDeDatas(usuarioId, min, max);
                } else if (minData.isPresent()) {
                    var min = minData.get();

                    return refeicaoService.buscaPorDataInicial(usuarioId, min);
                } else {
                    var max = maxData.get();

                    return refeicaoService.buscaPorDataFinal(usuarioId, max);
                }
            }

            return refeicaoService.listarRefeicoes(usuarioId);

        } catch (UsuarioNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
    }

}
