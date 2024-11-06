package br.edu.infnet.ricardo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Dieta {

    @Id
    private Long id;

    @OneToOne
    private Usuario usuario;
    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "dieta_id")
    @ToString.Exclude
    private List<Refeicao> refeicoes;

    public Dieta() {
        this.refeicoes = new ArrayList<>();
    }

    public void addRefeicao(Refeicao refeicao) {
        this.refeicoes.add(refeicao);
    }

    public double getCalorias() {
        return refeicoes.stream()
                .mapToDouble(Refeicao::getCalorias)
                .sum();
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
        Dieta dieta = (Dieta) o;
        return getUsuario() != null && Objects.equals(getUsuario(), dieta.getUsuario());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy hp) {
            return hp.getHibernateLazyInitializer().getPersistentClass().hashCode();
        }
        return getClass().hashCode();
    }
}
