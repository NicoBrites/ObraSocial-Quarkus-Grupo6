package quarkus.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import quarkus.entity.Ubicacion;

public record EspecialistaRequest( 
    
    @NotNull
    String nombre,
    @NotNull
    String especialidad,
    @NotNull
    LocalTime horaEntrada,
    @NotNull
    LocalTime horaSalida,
    @NotEmpty
    Ubicacion ubicacion

    ) {

}


