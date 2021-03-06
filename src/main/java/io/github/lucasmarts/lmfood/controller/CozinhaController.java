package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.Cozinha;
import io.github.lucasmarts.lmfood.domain.service.CadastroCozinhaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cozinhas")
public class CozinhaController {

    private final CadastroCozinhaService cadastroCozinhaService;

    public CozinhaController(CadastroCozinhaService cadastroCozinhaService) {
        this.cadastroCozinhaService = cadastroCozinhaService;
    }

    @GetMapping
    public ResponseEntity<List<Cozinha>> listar() {
        return new ResponseEntity<>(cadastroCozinhaService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        return new ResponseEntity<>(cadastroCozinhaService.porId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cozinha> adicionar(@Valid @RequestBody Cozinha cozinha){
        return new ResponseEntity<>(cadastroCozinhaService.adicionar(cozinha), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
        return new ResponseEntity<>(cadastroCozinhaService.atualizar(id, cozinha), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        cadastroCozinhaService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
