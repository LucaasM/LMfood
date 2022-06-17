package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.Estado;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeEmUsoException;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.service.CadastroEstadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private final CadastroEstadoService cadastroEstadoService;

    public EstadoController(CadastroEstadoService cadastroEstadoService) {
        this.cadastroEstadoService = cadastroEstadoService;
    }

    @GetMapping
    public ResponseEntity<List<Estado>> listar(){
        return new ResponseEntity<>(cadastroEstadoService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estado> porId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cadastroEstadoService.porId(id), HttpStatus.OK);
        } catch(EntidadeNaoEncontradaException e) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody @Valid Estado estado){
        return new ResponseEntity<>(cadastroEstadoService.adicionar(estado), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            cadastroEstadoService.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(EntidadeEmUsoException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }
}
