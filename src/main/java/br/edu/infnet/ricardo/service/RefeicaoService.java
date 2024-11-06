package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.repository.IngredientePorcaoRepository;
import br.edu.infnet.ricardo.repository.RefeicaoRepository;
import br.edu.infnet.ricardo.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final UsuarioRepository usuarioRepository;

    public void salva(Refeicao refeicao) {

        ingredientePorcaoRepository.saveAll(refeicao.getIngredientes());
        refeicaoRepository.save(refeicao);
    }

    public List<Refeicao> listarRefeicoes(Long usuarioId) throws UsuarioNotFoundException {

        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioId(usuarioId);
    }

    private void verificaUsuario(Long usuarioId) throws UsuarioNotFoundException {
        final var usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isEmpty()) {
            throw new UsuarioNotFoundException("Usuário com id " + usuarioId + " não encontrado");
        }
    }

    public List<Refeicao> buscaPorData(Long usuarioId, LocalDate data) throws UsuarioNotFoundException {

        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioIdAndData(usuarioId, data);
    }

    public List<Refeicao> buscaPorIntervaloDeDatas(Long usuarioId, LocalDate min, LocalDate max) throws UsuarioNotFoundException {

        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioIdAndDataBetween(usuarioId, min, max);
    }

    public List<Refeicao> buscaPorDataInicial(Long usuarioId, LocalDate min) throws UsuarioNotFoundException {

        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioIdAndDataGreaterThanEqual(usuarioId, min);
    }

    public List<Refeicao> buscaPorDataFinal(Long usuarioId, LocalDate max) throws UsuarioNotFoundException {

        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioIdAndDataBefore(usuarioId, max);
    }

    public Optional<Refeicao> buscaPorId(Long id) {
        return refeicaoRepository.findById(id);
    }
}
