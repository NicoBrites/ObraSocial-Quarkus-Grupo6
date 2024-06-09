package quarkus.entity;

import java.time.LocalTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Especialista extends PanacheEntity {
	
	private String nombre;
	private String especialidad;	
	private LocalTime horarioEntrada;	
	private LocalTime horarioSalida;	
	private Ubicacion ubicacion;
	private Boolean estaBorrado;

}
