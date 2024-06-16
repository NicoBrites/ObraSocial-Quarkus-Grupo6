package quarkus.service;

import quarkus.dto.LoginRequest;
import quarkus.dto.RegisterRequest;
import quarkus.entity.Usuario;

public interface IAuthService {
    Usuario register (RegisterRequest RegisterRequest);

    String generateJwt(Usuario usuario);

    boolean userExiste(String username);

    String authenticate (LoginRequest loginRequest);
}
