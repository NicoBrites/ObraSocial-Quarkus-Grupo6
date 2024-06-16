package quarkus.service.impl;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import quarkus.dto.LoginRequest;
import quarkus.dto.RegisterRequest;
import quarkus.entity.Usuario;
import quarkus.exception.IncorrectUsernameOrPasswordException;
import quarkus.exception.UsernameAlreadyExistsException;
import quarkus.service.IAuthService;
import quarkus.service.IUsuarioService;

import java.time.Duration;

@ApplicationScoped
public class AuthServiceImpl implements IAuthService {

    @Inject
    private IUsuarioService usuarioService;

    @Override
    public Usuario register(RegisterRequest RegisterRequest) {
        if (userExiste(RegisterRequest.username())) {
            throw new UsernameAlreadyExistsException();
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

    @Override
    public String generateJwt(Usuario user) {
        return Jwt.issuer("backend-obra-social-grupo-6")
                .upn(user.getUsername())
                .groups(user.getRol())
                .expiresIn(Duration.ofDays(1))
                .sign();
    }


    @Override
    public String authenticate(LoginRequest loginRequest) {
        Usuario user = usuarioService.findByUsername(loginRequest.username())
                .orElseThrow(IncorrectUsernameOrPasswordException::new);
        if (!BcryptUtil.matches(loginRequest.password(), user.getPassword())) throw new IncorrectUsernameOrPasswordException();

        return generateJwt(user);
    }

    @Override
    public boolean userExiste(String username) {

        return usuarioService.findByUsername(username).isPresent();

    }
}
