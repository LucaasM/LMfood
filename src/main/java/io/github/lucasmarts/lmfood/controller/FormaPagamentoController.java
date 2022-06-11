package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.FormaPagamento;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @GetMapping
    public ResponseEntity<List<FormaPagamento>> listar(){
        return new ResponseEntity<>(cadastroFormaPagamentoService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> porId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cadastroFormaPagamentoService.porId(id), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> adicionar(@RequestBody FormaPagamento formaPagamento) {
        return new ResponseEntity<>(cadastroFormaPagamentoService.adicionar(formaPagamento), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        try {
            cadastroFormaPagamentoService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
