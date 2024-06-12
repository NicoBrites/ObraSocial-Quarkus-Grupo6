package quarkus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecetaRequest(
    @NotNull
    Long turnoId,
    @NotBlank
    String receta
) {

}
