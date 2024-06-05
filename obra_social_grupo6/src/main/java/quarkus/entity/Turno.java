package quarkus.entity;


import java.time.LocalDate;
import java.time.LocalTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity 
public class Turno extends PanacheEntity {
	
	@ManyToOne
	@JoinColumn(name = "paciente_id")
	Paciente paciente;  
	
	@ManyToOne
	@JoinColumn(name = "especialista_id")
	private Especialista especialista;
	
	private LocalDate fecha;
	
	private LocalTime hora;
	
	private String motivoConsultaString;
	
	private Boolean estado;
}
