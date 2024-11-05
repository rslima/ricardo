package br.edu.infnet.ricardo.controller;


import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.service.RefeicaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/refeicoes")
@RequiredArgsConstructor
@Slf4j
public class RefeicaoController {
    private final RefeicaoService refeicaoService;

    @GetMapping
    public List<Refeicao> todasRefeicoes() {
        return refeicaoService.todas();
    }

}
