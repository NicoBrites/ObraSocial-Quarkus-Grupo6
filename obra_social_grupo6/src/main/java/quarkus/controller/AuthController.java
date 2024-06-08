package quarkus.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import quarkus.dto.LoginRequest;
import quarkus.dto.RegisterRequest;
import quarkus.service.AuthService;

@Path("api/v1/auth")
public class AuthController {


    @Inject
    private AuthService authService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response register (RegisterRequest registerRequest){
            authService.register(registerRequest);
            return Response.status(Response.Status.CREATED).entity("Usuario creado correctamente").build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response login(LoginRequest loginRequest){
        var token = authService.authenticate(loginRequest);
        return Response.ok(token).build();
    }

}
