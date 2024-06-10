package quarkus.dto;

import java.time.LocalDate;

public record RecetaDto(
    Long codigoTurno,
    String nombreMedico,
    String especiliadad,
    String receta,
    LocalDate fechaCreacion
) {

}
