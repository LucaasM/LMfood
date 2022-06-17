package io.github.lucasmarts.lmfood.controller;

import io.github.lucasmarts.lmfood.domain.entity.Restaurante;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.service.CadastroRestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final CadastroRestauranteService cadastroRestauranteService;

    public RestauranteController(CadastroRestauranteService cadastroRestauranteService) {
        this.cadastroRestauranteService = cadastroRestauranteService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurante>> todos() {
        return new ResponseEntity<>(cadastroRestauranteService.todos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> porId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(cadastroRestauranteService.porId(id), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            return new ResponseEntity<>(cadastroRestauranteService.adicionar(restaurante), HttpStatus.CREATED);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            return new ResponseEntity<>(cadastroRestauranteService.atualizar(id, restaurante), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atuilizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> requestRestaurante) {

        try {
            return new ResponseEntity<>(cadastroRestauranteService.atualizarParcial(id, requestRestaurante), HttpStatus.OK);
        } catch (EntidadeNaoEncontradaException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            cadastroRestauranteService.remove(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntidadeNaoEncontradaException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
