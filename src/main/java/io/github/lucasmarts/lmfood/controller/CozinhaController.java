package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.Cozinha;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeEmUsoException;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    @GetMapping
    public ResponseEntity<List<Cozinha>> listar() {
        return new ResponseEntity<>(cadastroCozinhaService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cadastroCozinhaService.porId(id), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@Valid @RequestBody Cozinha cozinha){
        return new ResponseEntity<>(cadastroCozinhaService.adicionar(cozinha), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {

        try {
            return new ResponseEntity<>(cadastroCozinhaService.atualizar(id, cozinha), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cadastroCozinhaService.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntidadeEmUsoException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch(EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
}
