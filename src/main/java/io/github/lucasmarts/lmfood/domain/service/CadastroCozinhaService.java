package io.github.lucasmarts.lmfood.domain.service;

import io.github.lucasmarts.lmfood.domain.entity.Cozinha;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeEmUsoException;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha adicionar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public List<Cozinha> todos() {
        return cozinhaRepository.findAll();
    }

    public Cozinha porId(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cozinha com código %d", id)
        ));
    }

    public Cozinha atualizar(Long id, Cozinha cozinha) {
        Cozinha cozinhaPersistida = porId(id);

        cozinhaPersistida.setNome(cozinha.getNome());
        return cozinhaRepository.save(cozinhaPersistida);
    }

    public void remove(Long id) {
        try {
            cozinhaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cozinha com código %d", id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cozinha de código %d não pode ser removida, pois está em uso", id)
            );
        }

    }


}
