package quarkus.dto;

import java.time.LocalDate;

public record RecetaDto(
    Long turnoId,
    String receta,
    LocalDate fechaCreacion,
    LocalDate fechaValidez
) {

}
