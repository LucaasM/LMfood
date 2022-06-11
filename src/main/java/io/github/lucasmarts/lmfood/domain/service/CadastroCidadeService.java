package io.github.lucasmarts.lmfood.domain.service;

import io.github.lucasmarts.lmfood.domain.entity.Cidade;
import io.github.lucasmarts.lmfood.domain.entity.Estado;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.repository.CidadeRepository;
import io.github.lucasmarts.lmfood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Cidade> todos() {
        return cidadeRepository.findAll();
    }

    public Cidade porId(Long id) {
        return cidadeRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe uma cidade com o código %d", id)
        ));

    }

    public Cidade adicionar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe uma estado com o código %d", estadoId)
        ));

        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public Cidade atualizar(Long id, Cidade cidade) {
        Cidade cidadePersitida = porId(id);

        Estado estado = estadoRepository.findById(cidade.getEstado().getId()).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe uma estado com o código %d", cidade.getEstado().getId())
        ));

        cidadePersitida.setNome(cidade.getNome());
        cidadePersitida.setEstado(estado);
        return cidadeRepository.save(cidadePersitida);
    }

    public void remove(Long id) {
        try {
            cidadeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com código %d", id)
            );
        }
    }


}
