package quarkus.controller;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.dto.RecetaDto;
import quarkus.service.IRecetaService;



@Path("/recetas")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("PACIENTE")
public class RecetaController {

    @Inject
	private IRecetaService recetaServiceImpl;
	 
	@GET	
    @Path("/{id}")
	public Response get(@PathParam("id")Long id, @HeaderParam("Authorization") String authorizationHeader) {
        RecetaDto receta = recetaServiceImpl.getReceta(id, authorizationHeader);
		return Response.status(Response.Status.OK).entity(receta).build();

    }
}
