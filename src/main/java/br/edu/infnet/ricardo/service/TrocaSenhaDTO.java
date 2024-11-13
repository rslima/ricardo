package br.edu.infnet.ricardo.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrocaSenhaDTO {
    private Long id;
    @NotNull @Size(min=8)
    private String senha;
}
