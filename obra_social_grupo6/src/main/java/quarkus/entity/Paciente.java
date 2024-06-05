package quarkus.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @UserDefinition 
public class Paciente extends PanacheEntity {
	
	@Username 
	private String username;
	@Password 
	private String password;
	private Integer numeroAfiliado;
    private String nombre;
    private String direccion;
    @Roles
    private String rol;
    
}
