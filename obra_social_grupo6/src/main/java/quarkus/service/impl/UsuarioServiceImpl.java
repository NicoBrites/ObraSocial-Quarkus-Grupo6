package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import quarkus.entity.Usuario;
import quarkus.repository.UsuarioRepository;
import quarkus.service.UsuarioService;

import java.util.Optional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {

        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.persist(usuario);
    }


}
