package io.github.lucasmarts.lmfood.domain.entity;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class Endereco {

    @JoinColumn(name = "endereco_restaurante_cep")
    private String cep;

    @JoinColumn(name = "endereco_restaurante_logradouro")
    private String logradouro;

    @JoinColumn(name = "endereco_restaurante_bairro")
    private String bairro;

    @JoinColumn(name = "endereco_restaurante_numero")
    private String numero;

    @JoinColumn(name = "endereco_restaurante_cidade")
    private String cidade;
}
