package quarkus.service.impl;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import quarkus.dto.LoginRequest;
import quarkus.dto.RegisterRequest;
import quarkus.entity.Usuario;
import quarkus.service.AuthService;
import quarkus.service.UsuarioService;

import java.time.Duration;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    private UsuarioService usuarioService;

    @Override
    public Usuario register(RegisterRequest RegisterRequest) {
        if (userExiste(RegisterRequest.username())) {
            throw new RuntimeException("Usuario ya esta registrado");
        }
        var newUser = Usuario.builder()
                .username(RegisterRequest.username())
                .password(BcryptUtil.bcryptHash(RegisterRequest.password()))
                .numeroAfiliado(RegisterRequest.numeroAfiliado())
                .nombre(RegisterRequest.nombre())
                .apellido(RegisterRequest.apellido())
                .direccion(RegisterRequest.direccion())
                .rol("PACIENTE")
                .build();
        usuarioService.save(newUser);
        return newUser;
    }

    //TODO cambiar los runtimeException por WebApplicationException
    @Override
    public String generateJwt(String username) {
        var user = usuarioService.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return Jwt.issuer("backend-obra-social-grupo-6")
                .upn(user.getUsername())
                .groups(user.getRol())
                .expiresIn(Duration.ofDays(1))
                .sign();


    }


    @Override
    public String authenticate(LoginRequest loginRequest) {
        Usuario user = usuarioService.findByUsername(loginRequest.username()).orElseThrow(() -> new RuntimeException("Usuario o contrasenia incorrectos"));
        if (!BcryptUtil.matches(loginRequest.password(), user.getPassword())) throw new RuntimeException("Usuario o contrasenia incorrectos");

        return generateJwt(loginRequest.username());
    }

    @Override
    public boolean userExiste(String username) {

        return usuarioService.findByUsername(username).isPresent();

    }
}
