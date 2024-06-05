package quarkus.entity;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Receta extends PanacheEntity {
	
	@OneToOne
	private Turno turno;  
	
	private String receta;
	
	private LocalDate fechaCreacionDate;
	
	private LocalDate fechaValidezDate;
	

}
