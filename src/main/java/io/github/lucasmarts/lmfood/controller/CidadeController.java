package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.Cidade;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> todos() {
        return new ResponseEntity<List<Cidade>>(cadastroCidadeService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cadastroCidadeService.porId(id), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            return new ResponseEntity<>(cadastroCidadeService.adicionar(cidade), HttpStatus.CREATED);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade){
        try {
            return new ResponseEntity<>(cadastroCidadeService.atualizar(id, cidade), HttpStatus.OK);
        } catch ( EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            cadastroCidadeService.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
