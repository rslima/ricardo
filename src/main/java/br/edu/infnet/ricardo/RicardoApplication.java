package br.edu.infnet.ricardo;

import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.utils.SpringDocAnnotationsUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class RicardoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RicardoApplication.class, args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "Ricardo");
    }

    @Bean
    public OpenAPI openAPI() {
        SpringDocAnnotationsUtils.removeAnnotationsToIgnore(Map.class);
        return new OpenAPI()
                .info(
                        new Info().title("API do Ricardo")
                                .version("0.0.5")
                                .description("API para acesso às funcionalidades do controle de dieta")
                                .contact(
                                        new Contact().name("Ricardo da Silva Lima")
                                                .email("rslima@gmail.com")))
                ;
    }

}
