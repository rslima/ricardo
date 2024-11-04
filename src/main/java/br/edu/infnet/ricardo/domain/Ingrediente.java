package br.edu.infnet.ricardo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Ingrediente {
    @Id
    @GeneratedValue
    private Long codigo;
    private String nome;
    private double caloriasPorPorcao;

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
        Ingrediente that = (Ingrediente) o;
        return getCodigo() != null && Objects.equals(getCodigo(), that.getCodigo());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy hp) {
            return hp.getHibernateLazyInitializer().getPersistentClass().hashCode();
        }
        return getClass().hashCode();
    }
}
