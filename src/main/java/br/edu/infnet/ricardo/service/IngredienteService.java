package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Ingrediente;
import br.edu.infnet.ricardo.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class IngredienteService {
    private final IngredienteRepository ingredienteRepository;

    public Ingrediente salvar(Ingrediente ingrediente) throws IngredienteNotFoundException {

        var ingrediente1 = Optional.<Ingrediente>empty();

        if (ingrediente.getId() != null) {
            ingrediente1 = ingredienteRepository.findById(ingrediente.getId());
        }

        if (ingrediente1.isPresent() || ingrediente.getId() == null) {
            return ingredienteRepository.save(ingrediente);
        }

        throw new IngredienteNotFoundException(ingrediente.getId());

    }

    public List<Ingrediente> todos() {
        return ingredienteRepository.findAll();
    }

    public Optional<Ingrediente> obtemPorId(long id) {
        return ingredienteRepository.findById(id);
    }

    public void deletarPorId(long id) throws IngredienteNotFoundException {
        final var i = obtemPorId(id);
        if (i.isPresent()) {
            ingredienteRepository.deleteById(id);
        } else {
            throw new IngredienteNotFoundException(id);
        }
    }
}
