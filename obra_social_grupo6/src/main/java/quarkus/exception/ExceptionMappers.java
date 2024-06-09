package quarkus.exception;

import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
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
    public Response handleRecetaException(RecetaException recetaException) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(recetaException.getMessage()).build();
    }

}
