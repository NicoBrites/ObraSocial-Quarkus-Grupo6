package quarkus.dto;

import java.time.LocalDate;

public record RecetaDto(
    String codigoTurno,
    String nombreMedico,
    String especiliadad,
    String receta,
    LocalDate fechaCreacion
) {

}
