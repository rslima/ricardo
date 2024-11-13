package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.controller.IngredientePorcaoDTO;
import br.edu.infnet.ricardo.controller.RefeicaoDTO;
import br.edu.infnet.ricardo.domain.IngredientePorcao;
import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.repository.DietaRepository;
import br.edu.infnet.ricardo.repository.IngredientePorcaoRepository;
import br.edu.infnet.ricardo.repository.IngredienteRepository;
import br.edu.infnet.ricardo.repository.RefeicaoRepository;
import br.edu.infnet.ricardo.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private final IngredienteRepository ingredienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final DietaRepository dietaRepository;

    public void salva(Refeicao refeicao) {

        ingredientePorcaoRepository.saveAll(refeicao.getIngredientes());
        refeicaoRepository.save(refeicao);
    }

    public Refeicao salva(Long usuarioId, RefeicaoDTO refeicaoDTO) throws UsuarioNotFoundException, IngredienteNotFoundException {
        final var refeicao = new Refeicao();

        if (refeicaoDTO.getId() != null) {
            refeicao.setId(refeicaoDTO.getId());
        }

        refeicao.setData(refeicaoDTO.getData());
        refeicao.setTipo(refeicaoDTO.getTipo());
        refeicao.setDieta(dietaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException(usuarioId)));

        final var ingredientesRefeicao = new ArrayList<IngredientePorcao>();

        for (IngredientePorcaoDTO ingredienteDTO : refeicaoDTO.getIngredientes()) {
            final var ingredientePorcao = new IngredientePorcao();
            ingredientePorcao.setPorcao(ingredienteDTO.getPorcao());

            ingredientePorcao.setIngrediente(
                    ingredienteRepository.findById(ingredienteDTO.getIdIngrediente())
                            .orElseThrow(() -> new IngredienteNotFoundException(ingredienteDTO.getIdIngrediente())));

            ingredientesRefeicao.add(ingredientePorcao);

        }

        ingredientePorcaoRepository.saveAll(ingredientesRefeicao);

        refeicao.setIngredientes(ingredientesRefeicao);


        return refeicao;
    }

    public List<Refeicao> listarRefeicoes(Long usuarioId) throws UsuarioNotFoundException {

        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioId(usuarioId);
    }

    private void verificaUsuario(Long usuarioId) throws UsuarioNotFoundException {
        final var usuario = usuarioRepository.findById(usuarioId);

        if (usuario.isEmpty()) {
            throw new UsuarioNotFoundException(usuarioId);
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

    public Optional<Refeicao> buscaPorId(Long usuarioId, Long id) throws UsuarioNotFoundException {
        verificaUsuario(usuarioId);

        return refeicaoRepository.findByDietaUsuarioIdAndId(usuarioId, id);
    }

    public void excluiById(Long usuarioId, Long refeicaoId) throws UsuarioNotFoundException {

        Optional<Refeicao> refeicao = buscaPorId(usuarioId, refeicaoId);

        if (refeicao.isPresent()) {
            refeicaoRepository.deleteById(refeicaoId);
        }
    }
}
