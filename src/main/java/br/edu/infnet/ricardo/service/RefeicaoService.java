package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.repository.IngredientePorcaoRepository;
import br.edu.infnet.ricardo.repository.RefeicaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static jakarta.transaction.Transactional.TxType.REQUIRED;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(REQUIRED)
public class RefeicaoService {
    private final RefeicaoRepository refeicaoRepository;
    private final IngredientePorcaoRepository ingredientePorcaoRepository;

    public void salva(Refeicao refeicao) {

        ingredientePorcaoRepository.saveAll(refeicao.getIngredientes());
        refeicaoRepository.save(refeicao);
    }

    public List<Refeicao> todas() {
        return refeicaoRepository.findAll();
    }

    public Optional<Refeicao> buscaPorId(Long id) {
        return refeicaoRepository.findById(id);
    }
}
