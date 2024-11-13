package br.edu.infnet.ricardo.loader;

import br.edu.infnet.ricardo.domain.InNatura;
import br.edu.infnet.ricardo.domain.Ingrediente;
import br.edu.infnet.ricardo.domain.Processado;
import br.edu.infnet.ricardo.service.IngredienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.csv.CSVFormat.DEFAULT;

@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class IngredienteCSVLoader implements ApplicationRunner {

    private final IngredienteService ingredienteService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        CSVParser parse = CSVParser.parse(new ClassPathResource("ingredientes.csv").getInputStream(), UTF_8, DEFAULT);
        for (CSVRecord r : parse.getRecords()) {
            final var tipoIngrediente = r.get(3).trim();

            final var i = tipoIngrediente.equals("N") ?
                    getIngredienteInNatura(r) :
                    getIngredienteProcessado(r);

            ingredienteService.salvar(i);

            log.info(i.toString());
        }
    }

    private static Ingrediente getIngredienteInNatura(CSVRecord r) {
        var n = new InNatura();

        n.setNome(r.get(0).trim());
        n.setCaloriasPorPorcao(Double.parseDouble(r.get(1).trim()));
        n.setDescricaoPorcao(r.get(2).trim());
        n.setOrganico(Boolean.parseBoolean(r.get(4).trim()));
        n.setVegano(Boolean.parseBoolean(r.get(5).trim()));

        return n;
    }

    private static Ingrediente getIngredienteProcessado(CSVRecord r) {
        Ingrediente i;
        var p = new Processado();

        p.setNome(r.get(0).trim());
        p.setCaloriasPorPorcao(Double.parseDouble(r.get(1).trim()));
        p.setDescricaoPorcao(r.get(2).trim());
        p.setFabricante(r.get(4).trim());
        p.setEan(r.get(5).trim());

        i = p;
        return i;
    }

}
