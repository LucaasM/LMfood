package io.github.lucasmarts.lmfood.domain.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sub_total")
    private BigDecimal subTotal;

    @Column(name = "taxa_frete")
    private BigDecimal taxaFrete;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_criacao")
    private LocalDate dataCricao;

    @Column(name = "data_confirmacao")
    private LocalDate dataConfirmacao;

    @Column(name = "data_cancelamento")
    private LocalDate dataCancelamento;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @OneToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Embedded
    private Endereco endereco;

    @OneToOne(fetch = FetchType.LAZY)
    private Restaurante restaurante;

    @OneToOne(fetch = FetchType.LAZY)
    private FormaPagamento formaPagamento;

    @ManyToMany
    @JoinTable(name = "pedido_item_pedido",
                joinColumns = @JoinColumn(name = "pedido_id"),
                inverseJoinColumns = @JoinColumn(name = "item_pedido_id"))
    private List<ItemPedido> itemPedidoList = new ArrayList<>();
}
