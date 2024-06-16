package quarkus;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import quarkus.dto.LoginRequest;
import quarkus.dto.RegisterRequest;
import quarkus.entity.Usuario;
import quarkus.exception.IncorrectUsernameOrPasswordException;
import quarkus.exception.UsernameAlreadyExistsException;
import quarkus.service.impl.AuthServiceImpl;
import quarkus.service.impl.UsuarioServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    UsuarioServiceImpl usuarioService;

    @InjectMocks
    AuthServiceImpl authService;

    Usuario usuario;

    RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        usuario = Usuario.builder()
                .username("testuser")
                .password(BcryptUtil.bcryptHash("password123"))
                .numeroAfiliado(2324324)
                .nombre("test")
                .apellido("test1")
                .direccion("123 Main St")
                .rol("PACIENTE")
                .build();

        registerRequest = new RegisterRequest(
                "testuser",
                "password123",
                2324324,
                "John",
                "Doe",
                "123 Main St");

    }




    @Test
    void testRegisterSuccessful() {

        when(usuarioService.findByUsername(registerRequest.username())).thenReturn(Optional.empty());
        doNothing().when(usuarioService).save(any(Usuario.class));

        Usuario actualUser = authService.register(registerRequest);

        assertEquals(usuario.id, actualUser.id);

        verify(usuarioService, times(1)).save(any(Usuario.class));
        verify(usuarioService, times(1)).save(any(Usuario.class));

    }

    @Test
    void testRegister_UserAlreadyExists() {

        when(usuarioService.findByUsername(registerRequest.username())).thenReturn(Optional.of(new Usuario()));

        assertThrows(UsernameAlreadyExistsException.class, () -> {
            authService.register(registerRequest);
        });

        verify(usuarioService, times(1)).findByUsername(registerRequest.username());

    }


    @Test
    void testGenerateJwt() {

        String jwt = authService.generateJwt(usuario);

        assertNotNull(jwt);
        assertEquals(jwt.getClass(), String.class);
    }

    @Test
    void testAuthenticateSuccesful() {
        var loginRequest = new LoginRequest("testuser", "password123");

        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        String jwt = authService.authenticate(loginRequest);

        assertNotNull(jwt);
        verify(usuarioService, times(1)).findByUsername("testuser");


    }

    @Test
    void testAuthenticateFailureInvalidUsername() {
        LoginRequest loginRequest = new LoginRequest("incorrectUser", "password123");
        when(usuarioService.findByUsername(loginRequest.username())).thenReturn(Optional.empty());

        assertThrows(IncorrectUsernameOrPasswordException.class, () -> authService.authenticate(loginRequest));
        verify(usuarioService, times(1)).findByUsername(loginRequest.username());
    }


    @Test
    void testAuthenticateFailureInvalidPassword() {
        LoginRequest loginRequest = new LoginRequest("testuser", "wrongpassword");
        when(usuarioService.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        assertThrows(IncorrectUsernameOrPasswordException.class, () -> authService.authenticate(loginRequest));
        verify(usuarioService, times(1)).findByUsername("testuser");
    }


}