package quarkus.service;

import quarkus.entity.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findById(Long id);

    void save(Usuario usuario);
}
