package quarkus.dto;

public record RegisterRequest(
        String username,
        String password,
        Integer numeroAfiliado,
        String nombre,
        String apellido,
        String direccion
) {
}
