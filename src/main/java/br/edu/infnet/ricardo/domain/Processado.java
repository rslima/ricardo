package br.edu.infnet.ricardo.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Processado extends Ingrediente {
    private String fabricante;
    private String ean;
}
