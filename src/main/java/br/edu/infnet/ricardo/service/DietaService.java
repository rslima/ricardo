package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Dieta;
import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.repository.DietaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DietaService {
    private final DietaRepository dietaRepository;

    public void salvar(Dieta dieta) {
        dietaRepository.save(dieta);
    }

    public List<Dieta> todos() {
        return dietaRepository.findAll();
    }

    public Optional<Dieta> buscaPorIdUsuario(Long usuarioId) {
        return dietaRepository.findByUsuarioId(usuarioId);
    }

    public List<Refeicao> buscaPorData(Long usuarioId, LocalDate data) {
        final var dieta = buscaPorIdUsuario(usuarioId);
        return dieta.map(d ->
                        d.getRefeicoes()
                                .stream()
                                .filter(r -> r.getData().equals(data))
                                .toList())
                .orElse(List.of());
    }
}
