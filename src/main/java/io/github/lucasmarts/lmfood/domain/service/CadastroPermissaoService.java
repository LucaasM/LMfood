package io.github.lucasmarts.lmfood.domain.service;

import io.github.lucasmarts.lmfood.domain.entity.Permissao;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public List<Permissao> todos() {
        return permissaoRepository.findAll();
    }

    public Permissao porId(Long id) {
        return permissaoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe uma permissao com o código %d", id)
        ));
    }

    public Permissao adicionar(Permissao formaPagamento) {
        return permissaoRepository.save(formaPagamento);
    }

    public void remove(Long id) {
        try {
            permissaoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe uma permissao com o código %d", id)
            );
        }


    }
}