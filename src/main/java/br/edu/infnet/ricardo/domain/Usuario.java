package br.edu.infnet.ricardo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private int idade;
    private String email;
    private String senha;
    private Sexo sexo;
    private int altura;
    private double peso;
    private NivelAtividade nivelAtividade;

    public double getIMC() {
        return peso * 10_000 / (altura * altura);
    }

    public double getTMB() {
        return sexo == Sexo.FEMININO ? getTMBFeminino() : getTMBMasculino();
    }

    private double getTMBFeminino() {
        return 655.1 + 9.563 * peso + 1.850 * altura - 4.676 * idade;
    }

    private double getTMBMasculino() {
        return 66.5 + 13.75 * peso + 5.003 * altura - 6.755 * idade;
    }

    public double getGEDT() {
        return getTMB() * nivelAtividade.getFator();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass;
        if (o instanceof HibernateProxy hpo) {
            oEffectiveClass = hpo.getHibernateLazyInitializer().getPersistentClass();
        } else {
            oEffectiveClass = o.getClass();
        }

        Class<?> thisEffectiveClass;
        if (this instanceof HibernateProxy hpt) {
            thisEffectiveClass = hpt.getHibernateLazyInitializer().getPersistentClass();
        } else {
            thisEffectiveClass = this.getClass();
        }
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return getId() != null && Objects.equals(getId(), usuario.getId());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy hpt) {
            return hpt.getHibernateLazyInitializer().getPersistentClass().hashCode();
        }
        return getClass().hashCode();
    }
}
