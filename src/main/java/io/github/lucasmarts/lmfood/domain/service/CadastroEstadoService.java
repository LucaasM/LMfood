package io.github.lucasmarts.lmfood.domain.service;

import io.github.lucasmarts.lmfood.domain.entity.Estado;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeEmUsoException;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> todos() {
        return estadoRepository.findAll();
    }

    public Estado porId(Long id) {
        return estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe uma estado com o código %d", id)
        ));
    }

    public Estado adicionar (Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remove (Long id){

        try {
            estadoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com código %d", id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removida, pois está em uso", id)
            );
        }
    }
}
