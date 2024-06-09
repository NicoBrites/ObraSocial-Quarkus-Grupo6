package quarkus.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import quarkus.entity.Ubicacion;

public record EspecialistaRequest( 
    
    @Size(min = 1, max = 40, message = "El nombre debe tener entre 1 y 40 caracteres")
    String nombre,
    @NotBlank(message="La especialidad no puede estar vacio")
    String especialidad,
    @NotNull(message="La hora de entrada no puede estar vacia")
    LocalTime horaEntrada,
    @NotNull(message="La hora de salida no puede estar vacia")
    LocalTime horaSalida,
    @NotNull(message="La ubicacion no puede estar vacia")
    Ubicacion ubicacion

    ) {

}


