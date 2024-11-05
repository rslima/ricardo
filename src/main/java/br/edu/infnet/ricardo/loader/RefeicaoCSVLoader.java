package br.edu.infnet.ricardo.loader;

import br.edu.infnet.ricardo.domain.Ingrediente;
import br.edu.infnet.ricardo.domain.Refeicao;
import br.edu.infnet.ricardo.domain.TipoRefeicao;
import br.edu.infnet.ricardo.service.IngredienteService;
import br.edu.infnet.ricardo.service.RefeicaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.csv.CSVFormat.DEFAULT;

@Component
@RequiredArgsConstructor
@Order(3)
@Slf4j
public class RefeicaoCSVLoader implements ApplicationRunner {
    private final RefeicaoService refeicaoService;
    private final IngredienteService ingredienteService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Refeicao refeicao = null;
        Long idUltimaRefeicao = null;

        CSVParser parse = CSVParser.parse(new ClassPathResource("refeicoes.csv").getInputStream(), UTF_8, DEFAULT);
        final var records = parse.getRecords();

        for (var csvRecord : records) {
            Long idRefeicao = Long.parseLong(csvRecord.get(0).trim());
            if (!idRefeicao.equals(idUltimaRefeicao)) {
                idUltimaRefeicao = idRefeicao;
                if (refeicao != null) {
                    refeicaoService.salva(refeicao);
                }
                refeicao = new Refeicao();
                refeicao.setId(idRefeicao);
                refeicao.setData(LocalDate.parse(csvRecord.get(1).trim()));
                refeicao.setTipo(TipoRefeicao.valueOf(csvRecord.get(2).trim()));
            }

            final var optIngrediente = ingredienteService.obtemPorId(Long.parseLong(csvRecord.get(3).trim()));
            if (optIngrediente.isPresent()) {
                Ingrediente ingrediente = optIngrediente.get();
                final var tamanhoPorcao = Double.parseDouble(csvRecord.get(4).trim());
                refeicao.addIngrediente(ingrediente, tamanhoPorcao);
            }

        }

        if (refeicao != null) {
            refeicaoService.salva(refeicao);
        }

    }
}
