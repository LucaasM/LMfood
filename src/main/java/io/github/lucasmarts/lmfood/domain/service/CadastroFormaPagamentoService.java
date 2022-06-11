package io.github.lucasmarts.lmfood.domain.service;

import io.github.lucasmarts.lmfood.domain.entity.FormaPagamento;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadastroFormaPagamentoService{

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> todos() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento porId(Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe uma forma de pagamento com o código %d", id)
        ));
    }

    public FormaPagamento adicionar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public void remove(Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe uma forma de pagamento com o código %d", id)
            );
        }



    }
}
