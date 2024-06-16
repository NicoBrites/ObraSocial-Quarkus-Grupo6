package quarkus.configuration;

import jakarta.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title = "API Obra Social Grupo 6",
        version = "1.0.0",
        description = "Esta API gestiona las operaciones de la obra social del Grupo 6.\n Realizado por :\n" +
                "- Federico Acosta \n"  +
                "- David Bernhardt \n" +
                "- Nicolas Brites \n"
    )
)
public class SwaggerConfiguration extends Application {

}
