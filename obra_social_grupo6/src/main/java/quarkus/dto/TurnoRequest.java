package quarkus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record TurnoRequest(

        @NotNull
        Long pacienteId,
        @NotNull
        Long especialistaId,
        @NotNull
        LocalDate fecha,
        @NotNull
        LocalTime hora,
        @NotEmpty
        String motivoConsulta
        ) {
}
