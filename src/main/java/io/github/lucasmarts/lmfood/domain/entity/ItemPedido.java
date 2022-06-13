package io.github.lucasmarts.lmfood.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    @Column(name = "preco_total")
    private BigDecimal precoTotal;

    private String observacao;

    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

}
