package br.edu.infnet.ricardo.domain;

import lombok.Getter;

import java.util.Optional;

@Getter
public enum NivelAtividade {

    SEDENTARIO(1.2),
    POUCO_ATIVO(1.375),
    ATIVO(1.55),
    MUITO_ATIVO(1.725),
    ATLETA(1.9);

    private final double fator;

    NivelAtividade(double fator) {
        this.fator = fator;
    }

    public static Optional<NivelAtividade> fromInt(Integer i) {
        return switch (i) {
            case 1 -> Optional.of(SEDENTARIO);
            case 2 -> Optional.of(POUCO_ATIVO);
            case 3 -> Optional.of(ATIVO);
            case 4 -> Optional.of(MUITO_ATIVO);
            case 5 -> Optional.of(ATLETA);
            default -> Optional.empty();
        };
    }

}
