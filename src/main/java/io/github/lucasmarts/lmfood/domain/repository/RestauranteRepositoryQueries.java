package io.github.lucasmarts.lmfood.domain.repository;

import io.github.lucasmarts.lmfood.domain.entity.Restaurante;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepositoryQueries {

    List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
}
