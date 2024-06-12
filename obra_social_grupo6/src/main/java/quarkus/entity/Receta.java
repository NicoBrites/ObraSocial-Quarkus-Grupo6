package quarkus.entity;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Receta extends PanacheEntity {
	
	@OneToOne
	@JoinColumn(name = "turno_id")
	private Turno turno;  
	private String receta;
	private LocalDate fechaCreacion;
	private LocalDate fechaValidez;
	private Boolean estaBorrado;
	
}
