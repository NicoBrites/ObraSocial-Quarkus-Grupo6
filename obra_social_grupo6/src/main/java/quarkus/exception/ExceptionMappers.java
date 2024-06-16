package quarkus.exception;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {
    @ServerExceptionMapper
    public Response handleUserNameConflict(UsernameAlreadyExistsException usernameAlreadyExistsException) {
        return Response.status(Response.Status.CONFLICT).entity(usernameAlreadyExistsException.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return Response.status(Response.Status.NOT_FOUND).entity(userNotFoundException.getMessage()).build();
    }
    @ServerExceptionMapper
    public Response handleIncorrectUsernameOrPassword(IncorrectUsernameOrPasswordException incorrectUsernameOrPasswordException) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(incorrectUsernameOrPasswordException.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response handleTurnoException(TurnoException turnoException) {
        return Response.status(Response.Status.BAD_REQUEST).entity(turnoException.getMessage()).build();
    }
    @ServerExceptionMapper
    public Response handleRecetaException(RecetaException recetaException) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(recetaException.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response handleUnauthorizedException(UnauthorizedException unauthorizedException) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedException.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response handleRecetaNotFoundException(RecetaNotFoundException recetaException) {
        return Response.status(Response.Status.NOT_FOUND).entity(recetaException.getMessage()).build();
    }
}

