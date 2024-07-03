package quarkus.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;
import quarkus.service.ITurnoService;

@Path("/turnos")
@APIResponses(value = {
                @APIResponse(responseCode = "401", description = "No autorizado"),
                @APIResponse(responseCode = "500", description = "Error interno del servidor"),
                @APIResponse(responseCode = "400", description = "Error en la solicitud verifique los datos enviados")

})
public class TurnoController {

        @Inject
        ITurnoService turnoService;

        @GET
        @Produces("application/json")
        @RolesAllowed({ "PACIENTE", "ADMIN" })
        @APIResponses(value = {
                        @APIResponse(responseCode = "200", description = "Turnos encontrados correctamente", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = TurnoDto.class)))
        })
        public Response getAllTurnosByUser() {
                return Response.status(200).entity(turnoService.getAllByUser()).build();
        }

        @POST
        @Produces("application/json")
        @RolesAllowed({ "PACIENTE", "ADMIN" })

        @APIResponses(value = {
                        @APIResponse(responseCode = "201", description = "Turno creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = TurnoRequest.class))),
                        @APIResponse(responseCode = "404", description = "Usuario o Especialista no encontrado"),
        })
        public Response createTurno(@Valid TurnoRequest turnoRequest) {
                return Response.status(201).entity(turnoService.createTurno(turnoRequest)).build();
        }

        @PUT
        @Produces("application/json")
        @Path("/{id}")
        @RolesAllowed({ "PACIENTE", "ADMIN" })
        @APIResponses(value = {
                        @APIResponse(responseCode = "200", description = "Turno editado correctamente")
        })
        public Response updateTurno(@PathParam("id") Long id, @Valid TurnoRequest turnoRequest) {
                return Response.status(200).entity(turnoService.updateTurno(turnoRequest, id)).build();
        }

        @DELETE
        @Path("/{id}")
        @RolesAllowed({ "PACIENTE", "ADMIN" })
        @APIResponses(value = {
                        @APIResponse(responseCode = "200", description = "Turno eliminado correctamente")
        })
        public Response deleteTurno(@PathParam("id") Long id) {
                turnoService.deleteTurno(id);
                return Response.status(200).build();
        }

}
