package quarkus.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import quarkus.service.IEspecialistaService;


@Path("/especialistas")

public class EspecialistaController {

	@Inject
	private IEspecialistaService especialistaServiceImpl;

	@RolesAllowed("PACIENTE")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		return Response.ok(especialistaServiceImpl.getCartilla()).build();
	}

	@RolesAllowed("ADMIN")
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id){
		try{
		especialistaServiceImpl.delete(id);
		return Response.ok(200).build();
		}catch (Exception e){
			return Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
		}
	}
}
