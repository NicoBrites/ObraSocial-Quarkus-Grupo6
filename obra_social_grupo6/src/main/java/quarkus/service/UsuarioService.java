package quarkus.service;

import quarkus.entity.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> findByUsername(String username);

    void save(Usuario usuario);
}
