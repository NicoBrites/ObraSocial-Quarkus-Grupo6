package quarkus.dto;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "El username no puede estar vacio")
        String username,
        @NotEmpty(message = "La password no puede estar vacia")
        String password) {
}
