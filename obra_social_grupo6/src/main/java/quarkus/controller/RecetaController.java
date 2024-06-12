package quarkus.controller;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;
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
	public Response get(@PathParam("id")Long id) {
        RecetaDto receta = recetaServiceImpl.getReceta(id);
		return Response.status(Response.Status.OK).entity(receta).build();

    }

	@RolesAllowed("ADMIN")
	@POST
	@Consumes("application/json")
    @Produces("application/json")
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "201",
				description = "Crea una receta",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = RecetaRequest.class))),	
			@APIResponse(
				responseCode = "400",
				description = "Error: Bad Request"),            
            @APIResponse(
                responseCode = "500",
                description = "Error interno del servidor")
		}
    )
	public Response save(@Valid RecetaRequest recetaRequest){
		return Response.status(Response.Status.CREATED)
			.entity(recetaServiceImpl.save(recetaRequest)).build();
	}
}
