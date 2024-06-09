package quarkus.controller;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import quarkus.dto.EspecialistaDto;
import quarkus.dto.EspecialistaRequest;
import quarkus.entity.Especialista;
import quarkus.service.IEspecialistaService;


@Path("/especialistas")

public class EspecialistaController {

	@Inject
	private IEspecialistaService especialistaServiceImpl;

	@RolesAllowed("PACIENTE")
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
				description = "Trae la cartilla de medicos",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = Especialista.class)))
	            }
	    )
	public Response get() {
		return Response.ok(especialistaServiceImpl.getCartilla()).build();
	}

	@RolesAllowed("ADMIN")
	@DELETE
	@Path("/{id}")
	@APIResponses(
        value = {
            @APIResponse(
                responseCode = "200",
                description = "El especialista fue borrado"),
            @APIResponse(
                responseCode = "404",
                description = "Especialista no encontrado"),
            @APIResponse(
                responseCode = "500",
                description = "Error interno del servidor")
        }
    )
	public Response delete(@PathParam("id") Long id){
		especialistaServiceImpl.delete(id);
		return Response.ok().build();
	}

	@RolesAllowed("ADMIN")
	@POST
	@Produces("application/json")
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
				description = "Crea un especialista",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = Especialista.class))),	            
            @APIResponse(
                responseCode = "500",
                description = "Error interno del servidor")
		}
    )
	public Response save(EspecialistaRequest especialistaRequest){
		EspecialistaDto especialistaDTO = especialistaServiceImpl.save(especialistaRequest);
		return Response.ok(especialistaDTO).build();
	}
}
