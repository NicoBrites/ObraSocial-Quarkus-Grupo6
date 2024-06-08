package quarkus.controller;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
//import jakarta.ws.rs.PathParam; ---------- testeo borrar antes de entregar
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import quarkus.service.IEspecialistaService;


@Path("/especialistas")
@Produces(MediaType.APPLICATION_JSON)
public class EspecialistaController {

	@Inject
	private IEspecialistaService especialistaServiceImpl;
	 
	@GET	
	public Response get() {
		return Response.ok(especialistaServiceImpl.getCartilla()).build();
	}
	
	/*@GET	-------------Testeo
	@Path("/{id}")
	public Response getbyid(@PathParam("id") Long id) {
		return Response.ok(especialistaServiceImpl.getByID(id)).build();
	}*/
	 
}
