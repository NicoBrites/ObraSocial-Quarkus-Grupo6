package quarkus.entity;

import java.time.LocalTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Especialista extends PanacheEntity {
	
	private String nombre;
	private String especialidad;	
	private LocalTime horarioEntrada;	
	private LocalTime horarioSalida;	
	private Ubicacion ubicacion;
	private Boolean estaBorrado;

}
