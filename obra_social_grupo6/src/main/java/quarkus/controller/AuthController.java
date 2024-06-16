package quarkus.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import quarkus.dto.LoginRequest;
import quarkus.dto.RegisterRequest;
import quarkus.service.IAuthService;

@Path("/auth")
@Produces(MediaType.TEXT_PLAIN)
@APIResponses(
        value = {
                @APIResponse(
                        responseCode = "500",
                        description = "Error interno del servidor"),
                @APIResponse(
                        responseCode = "400",
                        description = "Error en la solicitud verifique los datos enviados")

        }
)

public class AuthController {


    @Inject
    private IAuthService IAuthService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    @Path("register")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "201",
                            description = "Usuario Creado correctamente"),


            }
    )
    public Response register (RegisterRequest registerRequest){
            IAuthService.register(registerRequest);
            return Response.status(Response.Status.CREATED).entity("Usuario creado correctamente").build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Logeado Correctamente")
            }
    )
    public Response login(LoginRequest loginRequest){
        var token = IAuthService.authenticate(loginRequest);
        return Response.ok(token).build();
    }

}
