package quarkus.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecetaRequestUpdate(
    @NotNull(message = "No se puede crear una receta sin el ID del turno")
    Long turnoId,
    @NotBlank(message = "No se puede crear una receta vacia")
    String receta,
    @NotNull
    LocalDate fechaCreacion,
    @NotNull
    LocalDate fechaValidez
) {

}
