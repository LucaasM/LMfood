package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.Permissao;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.service.CadastroPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissao")
public class PermissaoController {

    @Autowired
    private CadastroPermissaoService cadastroPermissaoService;

    @GetMapping
    public ResponseEntity<List<Permissao>> listar(){
        return new ResponseEntity<>(cadastroPermissaoService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permissao> porId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cadastroPermissaoService.porId(id), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Permissao> adicionar(@RequestBody Permissao permissao) {
        return new ResponseEntity<>(cadastroPermissaoService.adicionar(permissao), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        try {
            cadastroPermissaoService.remove(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
