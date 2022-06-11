package io.github.lucasmarts.lmfood.domain.repository;

import io.github.lucasmarts.lmfood.domain.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
