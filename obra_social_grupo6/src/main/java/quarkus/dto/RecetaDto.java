package quarkus.dto;

import java.time.LocalDate;

public record RecetaDto(
    Long turnoId,
    String nombreMedico,
    String especiliadad,
    String receta,
    LocalDate fechaCreacion
) {

}
