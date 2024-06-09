package quarkus.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter @Setter @Entity @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Turno extends PanacheEntity {
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario paciente;
	
	@ManyToOne
	@JoinColumn(name = "especialista_id")
	private Especialista especialista;
	
	private LocalDate fecha;

	private LocalTime hora;
	
	private String motivoConsulta;
	
	private Boolean estado;
}
