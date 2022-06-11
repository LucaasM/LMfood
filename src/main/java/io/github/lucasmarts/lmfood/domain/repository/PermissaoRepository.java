package io.github.lucasmarts.lmfood.domain.repository;

import io.github.lucasmarts.lmfood.domain.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}

