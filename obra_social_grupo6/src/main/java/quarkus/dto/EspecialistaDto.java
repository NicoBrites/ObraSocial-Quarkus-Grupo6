package quarkus.dto;

import java.time.LocalTime;

import quarkus.entity.Ubicacion;

public record EspecialistaDto(
	Long id,
    String nombre,
	String especialidad,
	LocalTime horarioEntrada,
	LocalTime horarioSalida,
	Ubicacion ubicacion
) {
    
}
