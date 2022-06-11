package io.github.lucasmarts.lmfood.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lucasmarts.lmfood.domain.entity.Cozinha;
import io.github.lucasmarts.lmfood.domain.entity.Restaurante;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeNaoEncontradaException;
import io.github.lucasmarts.lmfood.domain.repository.CozinhaRepository;
import io.github.lucasmarts.lmfood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public List<Restaurante> todos() {
        return restauranteRepository.findAll();
    }

    public Restaurante porId(Long id){
        return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe um restaurante com o código %d", id)
        ));
    }

    public Restaurante adicionar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cozinha com código %d", cozinhaId)
        ));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cozinha com código %d", cozinhaId)
        ));

        Restaurante restaurantePersistido = porId(id);

        restaurantePersistido.setNome(restaurante.getNome());
        restaurantePersistido.setTaxaFrete(restaurante.getTaxaFrete());
        restaurantePersistido.setCozinha(cozinha);

        return restauranteRepository.save(restaurantePersistido);

    }

    public Restaurante atualizarParcial(Long id, Map<String, Object> requestRestaurante) {
        Restaurante restaurantePersistido = porId(id);

        restaurantePersistido = merge(requestRestaurante, restaurantePersistido);

        Long cozinhaId = restaurantePersistido.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
                String.format("Não existe um cadastro de cozinha com código %d", cozinhaId)
        ));

        restaurantePersistido.setCozinha(cozinha);
        return restauranteRepository.save(restaurantePersistido);
    }

    public void remove(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de restaurante com código %d", id)
            );
        }

    }

    public Restaurante merge (Map<String, Object> requestRestaurante, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(requestRestaurante, Restaurante.class);

        requestRestaurante.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });

        return restauranteDestino;
    }
}
