package io.github.lucasmarts.lmfood.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @CreationTimestamp
    private LocalDate dataCadastro;

    @ManyToMany
    @JoinTable(name = "usuario_grupo",
                joinColumns = @JoinColumn(name = "usuario_id"),
                inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private List<Grupo> grupoList = new ArrayList<>();
}
