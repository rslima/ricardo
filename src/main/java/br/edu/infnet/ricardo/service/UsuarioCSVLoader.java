package br.edu.infnet.ricardo.service;

import br.edu.infnet.ricardo.domain.NivelAtividade;
import br.edu.infnet.ricardo.domain.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import static br.edu.infnet.ricardo.domain.Sexo.FEMININO;
import static br.edu.infnet.ricardo.domain.Sexo.MASCULINO;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.csv.CSVFormat.DEFAULT;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class UsuarioCSVLoader implements ApplicationRunner {

    private final UsuarioService usuarioService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CSVParser parse = CSVParser.parse(new ClassPathResource("usuarios.csv").getInputStream(), UTF_8, DEFAULT);
        parse.getRecords()
                .forEach(r -> {
                    final var u = new Usuario();

                    u.setNome(r.get(0).trim());
                    u.setIdade(Integer.parseInt(r.get(1).trim()));
                    u.setEmail(r.get(2).trim());
                    u.setSenha(r.get(3).trim());
                    u.setSexo(r.get(4).trim().equals("F") ? FEMININO : MASCULINO);
                    u.setAltura(Integer.parseInt(r.get(5).trim()));
                    u.setPeso(Double.parseDouble(r.get(6).trim()));
                    NivelAtividade.fromInt(Integer.parseInt(r.get(7).trim())).ifPresent(u::setNivelAtividade);

                    usuarioService.salvar(u);

                    log.info(u.toString());
                });
    }
}
