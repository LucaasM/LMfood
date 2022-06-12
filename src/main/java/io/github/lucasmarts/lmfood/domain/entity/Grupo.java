package io.github.lucasmarts.lmfood.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany
    @JoinTable(name = "grupo_permissao",
                joinColumns = @JoinColumn(name = "grupo_id"),
                inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private List<Permissao> permissaoList = new ArrayList<>();
}
