package quarkus.exception;

import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {
    @ServerExceptionMapper
    public Response mapException(UsernameAlreadyExistsException usernameAlreadyExistsException) {
        return Response.status(Response.Status.CONFLICT).entity(usernameAlreadyExistsException.getMessage()).build();
    }
}
