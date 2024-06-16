package quarkus.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter @Setter @Entity @UserDefinition @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends PanacheEntity {


	@Username 
	private String username;
	@Password 
	private String password;
	private Integer numeroAfiliado;
    private String nombre;
	private String apellido;
    private String direccion;

	@OneToMany(mappedBy = "paciente")
	private List<Turno> turnos;
    @Roles
    private String rol;

	public Long getId () {
		return this.id;
	}

	public void setId (Long id) {
		this.id = id;
	}
    
}
