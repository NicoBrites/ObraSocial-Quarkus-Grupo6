package quarkus.controller;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import quarkus.dto.EspecialistaDto;
import quarkus.dto.EspecialistaRequest;
import quarkus.service.IEspecialistaService;


@Path("/especialistas")

public class EspecialistaController {

	@Inject
	private IEspecialistaService especialistaServiceImpl;

	
	@GET
	@RolesAllowed({"PACIENTE","ADMIN"})	
	@Produces("application/json")
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "200",
				description = "Trae la cartilla de medicos",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = EspecialistaDto.class))),
			@APIResponse(
				responseCode = "500",
				description = "Error interno del servidor")
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
	@Consumes("application/json")
    @Produces("application/json")
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "201",
				description = "Crea un especialista",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = EspecialistaDto.class))),	
			@APIResponse(
				responseCode = "400",
				description = "Error: Bad Request"),            
            @APIResponse(
                responseCode = "500",
                description = "Error interno del servidor")
		}
    )
	public Response save(@Valid EspecialistaRequest especialistaRequest){
 
		return Response.status(Response.Status.CREATED)
			.entity(especialistaServiceImpl.save(especialistaRequest)).build();
	}

	@RolesAllowed("ADMIN")
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
    @Produces("application/json")
	@APIResponses(
		value = {
			@APIResponse(
				responseCode = "201",
				description = "Modifica un especialista",
				content = @Content(mediaType = "application/json",
				schema = @Schema(type = SchemaType.ARRAY, implementation = EspecialistaDto.class))),	
			@APIResponse(
				responseCode = "400",
				description = "Error: Bad Request"), 
			@APIResponse(
                responseCode = "404",
                description = "Especialista no encontrado"),           
            @APIResponse(
                responseCode = "500",
                description = "Error interno del servidor")
		}
    )
	public Response update(@Valid EspecialistaDto especialistaRequest, @PathParam("id") Long id){

		return Response.status(Response.Status.CREATED)
			.entity(especialistaServiceImpl.update(especialistaRequest, id)).build();
	}
}
