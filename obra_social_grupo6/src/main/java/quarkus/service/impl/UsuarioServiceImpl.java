package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import quarkus.entity.Usuario;
import quarkus.repository.UsuarioRepository;
import quarkus.service.IUsuarioService;

import java.util.Optional;

@ApplicationScoped
public class UsuarioServiceImpl implements IUsuarioService {

    @Inject
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findByIdOptional(id);
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.persist(usuario);
    }


}
