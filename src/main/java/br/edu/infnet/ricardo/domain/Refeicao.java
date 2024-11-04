package br.edu.infnet.ricardo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@ToString
@Entity
public class Refeicao {
    @Id
    @GeneratedValue
    private Long codigo;
    private LocalDate data;
    private TipoRefeicao tipo;
    @OneToMany(fetch = EAGER, orphanRemoval = true)
    @JoinColumn(name = "refeicao_id")
    private List<IngredientePorcao> ingredientes;

    public Refeicao(long codigo, LocalDate data, TipoRefeicao tipo) {
        this.codigo = codigo;
        this.data = data;
        this.tipo = tipo;
        this.ingredientes = new ArrayList<>();
    }

    public Refeicao() {
        this.ingredientes = new ArrayList<>();
    }

    public void addIngrediente(Ingrediente ingrediente, double porcao) {
        final var ingredientePorcao = new IngredientePorcao();
        ingredientePorcao.setIngrediente(ingrediente);
        ingredientePorcao.setPorcao(porcao);
        ingredientes.add(ingredientePorcao);
    }

    public double getCalorias() {
        return ingredientes.stream()
                .mapToDouble(IngredientePorcao::getCalorias)
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
        Refeicao refeicao = (Refeicao) o;
        return getCodigo() != null && Objects.equals(getCodigo(), refeicao.getCodigo());
    }

    @Override
    public final int hashCode() {

        final int result;

        if (this instanceof HibernateProxy hpt) {
            result = hpt.getHibernateLazyInitializer().getPersistentClass().hashCode();
        } else {
            result = getClass().hashCode();
        }

        return result;
    }
}
