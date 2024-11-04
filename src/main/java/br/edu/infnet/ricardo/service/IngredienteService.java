package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Ingrediente;
import br.edu.infnet.ricardo.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class IngredienteService {
    private final IngredienteRepository ingredienteRepository;

    public Ingrediente salvar(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public List<Ingrediente> todos() {
        return ingredienteRepository.findAll();
    }
}
