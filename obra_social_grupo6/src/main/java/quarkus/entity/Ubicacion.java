package quarkus.entity;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable @Getter @Setter
public class Ubicacion {
	
	private String provincia;
	private String ciudad;

}
