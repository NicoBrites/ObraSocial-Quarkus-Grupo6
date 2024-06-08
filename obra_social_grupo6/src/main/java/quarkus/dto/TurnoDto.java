package quarkus.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoDto(
                            Long turnoId,
                            Long pacienteId,
                            Long especialistaId,
                            LocalDate fecha,
                            LocalTime hora,
                            String motivoConsulta,
                            Boolean estado

) {
}
