package quarkus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecetaRequest(
    @NotNull(message = "No se puede crear una receta sin el ID del turno")
    Long turnoId,
    @NotBlank(message = "No se puede crear una receta vacia")
    String receta
) {

}
