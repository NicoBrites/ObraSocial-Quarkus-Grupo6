package quarkus.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;
import quarkus.service.ITurnoService;

@Path("/turnos")
public class TurnoController {

    @Inject
    ITurnoService turnoService;

    @POST
    @Produces("application/json")
    @RolesAllowed({"PACIENTE","ADMIN"})
    public Response createTurno(@Valid TurnoRequest turnoRequest) {
        return Response.status(201).entity(turnoService.createTurno(turnoRequest)).build();
    }

    @PUT
    @Produces("application/json")
    @Path("/{id}")
    @RolesAllowed({"PACIENTE","ADMIN"})
    public Response updateTurno(@PathParam("id") Long id , @Valid TurnoRequest turnoRequest) {
        return Response.status(200).entity(turnoService.updateTurno(turnoRequest,id)).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"PACIENTE","ADMIN"})
    public Response deleteTurno(@PathParam("id") Long id) {
        turnoService.deleteTurno(id);
        return Response.status(200).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{id}")
    @RolesAllowed({"PACIENTE","ADMIN"})
    public Response getTurnosByUsername(@PathParam("id") Long id) {
        return Response.status(200).entity(turnoService.getAllByUserId(id)).build();
    }


}
