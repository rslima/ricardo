package br.edu.infnet.ricardo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class IngredientePorcao {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Ingrediente ingrediente;
    private double porcao;

    public double getCalorias() {
        return this.porcao * this.ingrediente.getCaloriasPorPorcao();
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
        IngredientePorcao that = (IngredientePorcao) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy hp) {
            return hp.getHibernateLazyInitializer().getPersistentClass().hashCode();
        }
        return getClass().hashCode();
    }
}
