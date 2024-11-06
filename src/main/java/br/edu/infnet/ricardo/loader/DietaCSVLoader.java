package br.edu.infnet.ricardo.loader;


import br.edu.infnet.ricardo.domain.Dieta;
import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.service.DietaService;
import br.edu.infnet.ricardo.service.RefeicaoService;
import br.edu.infnet.ricardo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.csv.CSVFormat.DEFAULT;

@Component
@Slf4j
@RequiredArgsConstructor
@Order(4)
public class DietaCSVLoader implements ApplicationRunner {

    private final DietaService dietaService;
    private final RefeicaoService refeicaoService;
    private final UsuarioService usuarioService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Long idUltimoUsuario = null;
        Dieta dieta = null;

        CSVParser parse = CSVParser.parse(new ClassPathResource("dietas.csv").getInputStream(), UTF_8, DEFAULT);
        final var records = parse.getRecords();

        for (var csvRecord : records) {
            Long idUsuario = Long.parseLong(csvRecord.get(0).trim());
            if (!idUsuario.equals(idUltimoUsuario)) {
               idUltimoUsuario = idUsuario;
               if (dieta != null) {
                   dietaService.salvar(dieta);
               }
               dieta = new Dieta();
               dieta.setUsuario(usuarioService.buscarPorId(idUsuario).orElse(null));
               dieta.setId(idUsuario);

            }

            final var optRefeicao = refeicaoService.buscaPorId(Long.parseLong(csvRecord.get(1).trim()));
            if (optRefeicao.isPresent()) {
                Refeicao refeicao = optRefeicao.get();
                dieta.addRefeicao(refeicao);
            }
        }

        if (dieta != null) {
            dietaService.salvar(dieta);
        }

    }
}
