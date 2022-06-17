package io.github.lucasmarts.lmfood.domain.entity;

import io.github.lucasmarts.lmfood.grupos.Grupos;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Cidade {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Valid
    @ConvertGroup(from = Default.class, to = Grupos.CadastroCidade.class)
    @NotNull
    @ManyToOne
    private Estado estado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cidade cidade = (Cidade) o;
        return id != null && Objects.equals(id, cidade.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
