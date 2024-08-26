package Breakdance.KBEProdukt;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Product API", version = "1.1", description = "Documentation for Product v1.1"))
public class KbeProduktApplication {

    public static void main(String[] args) {
        SpringApplication.run(KbeProduktApplication.class, args);
    }

}
