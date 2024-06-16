package quarkus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotEmpty(message = "El username no puede estar vacio")
        String username,
        @NotEmpty(message = "La password no puede estar vacia")
        String password,
        @NotNull(message = "El numero de afiliado no puede estar vacio")
        Integer numeroAfiliado,
        @NotEmpty(message = "El nombre no puede estar vacio")
        String nombre,
        @NotEmpty(message = "El apellido no puede estar vacio")
        String apellido,
        @NotEmpty(message = "La direccion no puede estar vacia")
        String direccion
) {
}
