package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.Dieta;
import br.edu.infnet.ricardo.repository.DietaRepository;
import br.edu.infnet.ricardo.repository.RefeicaoRepository;
import br.edu.infnet.ricardo.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DietaService {
    private final UsuarioRepository usuarioRepository;
    private final DietaRepository dietaRepository;
    private final RefeicaoRepository refeicaoRepository;

    public void salvar(Dieta dieta) {
        dietaRepository.save(dieta);
    }

    public List<Dieta> todos() {
        return dietaRepository.findAll();
    }

    public Optional<Dieta> buscaPorIdUsuario(Long usuarioId) {
        return dietaRepository.findByUsuarioId(usuarioId);
    }



}
