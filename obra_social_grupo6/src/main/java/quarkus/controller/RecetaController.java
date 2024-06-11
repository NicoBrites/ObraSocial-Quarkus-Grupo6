package quarkus.controller;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import quarkus.dto.RecetaDto;
import quarkus.service.IRecetaService;


@Path("/recetas")
public class RecetaController {

    @Inject
	private IRecetaService recetaServiceImpl;
	 
	@GET	
    @Path("/{id}")
    @RolesAllowed("PACIENTE")	
	@Produces("application/json")
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
				description = "Trae la receta",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = RecetaDto.class))),
            @APIResponse(
                responseCode = "401",
                description = "El id del turno no pertenece a tu Usuario o Ya se vencio la fecha de validez de la Receta"),
            @APIResponse(
                responseCode = "404",
                description = "Turno no encontrado"),
			@APIResponse(
				responseCode = "500",
				description = "Error interno del servidor")
	            }
	    )
	public Response get(@PathParam("id")Long id, @HeaderParam("Authorization") String authorizationHeader) {
        RecetaDto receta = recetaServiceImpl.getReceta(id, authorizationHeader);
		return Response.status(Response.Status.OK).entity(receta).build();

    }
}
