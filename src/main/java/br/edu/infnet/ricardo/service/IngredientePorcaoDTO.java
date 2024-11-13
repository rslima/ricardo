package br.edu.infnet.ricardo.service;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class IngredientePorcaoDTO {
    private Long idIngrediente;
    @Min(0)
    private double porcao;
}
