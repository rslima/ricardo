package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.TipoRefeicao;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RefeicaoDTO {
    private Long id;
    private LocalDate data;
    private TipoRefeicao tipo;
    private List<IngredientePorcaoDTO> ingredientes;
}
