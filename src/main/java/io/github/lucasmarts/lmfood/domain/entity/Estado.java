package io.github.lucasmarts.lmfood.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.lucasmarts.lmfood.grupos.Grupos;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Estado {

    @NotNull(groups = Grupos.CadastroCidade.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @JsonIgnore
    @OneToMany
    private List<Cidade> cidadeList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Estado estado = (Estado) o;
        return id != null && Objects.equals(id, estado.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
