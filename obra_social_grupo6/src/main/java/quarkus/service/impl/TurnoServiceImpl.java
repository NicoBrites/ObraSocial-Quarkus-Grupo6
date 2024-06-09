package quarkus.service.impl;

import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;
import quarkus.dto.mapper.TurnoMapper;
import quarkus.entity.Especialista;
import quarkus.entity.Usuario;
import quarkus.exception.TurnoException;
import quarkus.exception.UserNotFoundException;
import quarkus.repository.TurnoRepository;
import quarkus.service.IEspecialistaService;
import quarkus.service.ITurnoService;
import quarkus.service.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TurnoServiceImpl implements ITurnoService {

    @Inject
    private IEspecialistaService especialistaService;

    @Inject
    private IUsuarioService usuarioService;

    @Inject
    private TurnoRepository turnoRepository;

    @Inject
    private JsonWebToken jwt;




    @Override
    @Transactional
    public TurnoDto createTurno(TurnoRequest turnoRequest) {

        var paciente = usuarioService.findById(turnoRequest.pacienteId())
                .orElseThrow(() -> new UserNotFoundException("Paciente no encontrado"));
        Especialista especialista = especialistaService.getByID(turnoRequest.especialistaId())
                .orElseThrow( ()-> new UserNotFoundException("No se encontro especialista"));

        validarFechaYHora(especialista, paciente, turnoRequest);

        var hasTurnoAtDateTime = paciente.getTurnos().stream().anyMatch((turno)->
                turno.getFecha().isEqual(turnoRequest.fecha()) &&
                        turno.getHora().getHour() == turnoRequest.hora().getHour()); //los turnos duran 1 hora

        if(hasTurnoAtDateTime) {
            throw new TurnoException("Ya tenes un turno Programado en ese horario");
        }


        var nuevoTurno = TurnoMapper.requestToEntity(turnoRequest,paciente,especialista);
        nuevoTurno.setEstado(true);

        turnoRepository.persist(nuevoTurno);

        return TurnoMapper.EntityToDto(nuevoTurno);

    }


    @Override
    public void validarFechaYHora(Especialista especialista, Usuario paciente, TurnoRequest turnoRequest) {

        LocalDateTime fechaHoraTurno = turnoRequest.fecha().atTime(turnoRequest.hora());
        if(turnoRequest.fecha().getDayOfWeek().getValue() > 5) { // si el dia es sabado o domingo
            throw new TurnoException("El turno no puede ser programado en un dia no laboral");
        }

        if(fechaHoraTurno.isBefore(LocalDateTime.now())) {
            throw new TurnoException("El turno no puede ser programado en una fecha anterior");
        }

        if(turnoRequest.hora().isAfter(especialista.getHorarioSalida()) || turnoRequest.hora().isBefore(especialista.getHorarioEntrada())) {
            throw new TurnoException("El turno no puede ser programado fuera de horario");
        }

    }


    void ValidarUsuarioEstaAutorizado(Usuario paciente) {
        if(paciente.getRol().equals("ADMIN")) return;
        if(!paciente.getUsername().equals(jwt.getClaim("upn").toString())){
            throw new UnauthorizedException("No tenes permisos para esta accion");
        }
    }



    @Override
    @Transactional
    public TurnoRequest updateTurno(TurnoRequest turnoRequest, Long id) {

        var paciente = usuarioService.findById(turnoRequest.pacienteId())
                .orElseThrow(() -> new UserNotFoundException("Paciente no encontrado"));


        ValidarUsuarioEstaAutorizado(paciente);

        var turno = turnoRepository.findByIdOptional(id)
                .orElseThrow(() -> new TurnoException("No se encontro el turno"));

        Especialista especialista = especialistaService.getByID(turnoRequest.especialistaId())
                .orElseThrow( ()-> new UserNotFoundException("No se encontro especialista"));


        validarFechaYHora(especialista,paciente,turnoRequest);
        turno.setFecha(turnoRequest.fecha());
        turno.setHora(turnoRequest.hora());
        turno.setMotivoConsulta(turnoRequest.motivoConsulta());
        turno.setEspecialista(especialista);
        turnoRepository.persist(turno);
        return turnoRequest;
    }




    @Override
    public List<TurnoDto> getAllByUserId(Long id) {

        var paciente = usuarioService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Paciente no encontrado"));

        ValidarUsuarioEstaAutorizado(paciente);

        return turnoRepository.findAllByUserId(id).stream().map(TurnoMapper::EntityToDto).toList();

    }

    @Override
    @Transactional
    public void deleteTurno(Long id) {
        var paciente = usuarioService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Paciente no encontrado"));

        ValidarUsuarioEstaAutorizado(paciente);
        var turno =  turnoRepository.findByIdOptional(id)
                .orElseThrow(() -> new TurnoException("No se encontro el turno"));
        turnoRepository.delete(turno);
    }
}


