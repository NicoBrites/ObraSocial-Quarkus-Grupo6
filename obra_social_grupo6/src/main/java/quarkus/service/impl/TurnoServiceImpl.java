package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;
import quarkus.dto.mapper.TurnoMapper;
import quarkus.entity.Especialista;
import quarkus.exception.TurnoException;
import quarkus.exception.UserNotFoundException;
import quarkus.repository.TurnoRepository;
import quarkus.service.IEspecialistaService;
import quarkus.service.ITurnoService;
import quarkus.service.IUsuarioService;

import java.util.List;

@ApplicationScoped
public class TurnoServiceImpl implements ITurnoService {

    @Inject
    private IEspecialistaService especialistaService;

    @Inject
    private IUsuarioService usuarioService;

    @Inject
    private TurnoRepository turnoRepository;



    @Override
    @Transactional
    public TurnoDto createTurno(TurnoRequest turnoRequest) {

        if(turnoRequest.fecha().getDayOfWeek().getValue() > 5) { // si el dia es sabado o domingo
            throw new TurnoException("El turno no puede ser programado en un dia no laboral");
        }
        Especialista especialista = especialistaService.getByID(turnoRequest.especialistaId())
                .orElseThrow( ()-> new UserNotFoundException("No se encontro especialista"));

        if(turnoRequest.hora().isAfter(especialista.getHorarioSalida()) || turnoRequest.hora().isBefore(especialista.getHorarioEntrada())) {
            throw new TurnoException("El turno no puede ser programado fuera de horario");}

        var paciente = usuarioService.findById(turnoRequest.pacienteId())
                .orElseThrow(() -> new UserNotFoundException("Paciente no encontrado"));
        //TODO: validar que el especialista no tenga turnos en ese dia y hora

        var nuevoTurno = TurnoMapper.requestToEntity(turnoRequest,paciente,especialista);

        turnoRepository.persist(nuevoTurno);

        return TurnoMapper.EntityToDto(nuevoTurno);

    }

    @Override
    @Transactional
    public TurnoDto updateTurno(TurnoDto turnoDto, Long id) {
        var turno = turnoRepository.findByIdOptional(id)
                .orElseThrow(() -> new TurnoException("No se encontro el turno"));

        Especialista especialista = especialistaService.getByID(turnoDto.especialistaId())
                .orElseThrow( ()-> new UserNotFoundException("No se encontro especialista"));


        turno.setFecha(turnoDto.fecha());
        turno.setHora(turnoDto.hora());
        turno.setMotivoConsulta(turnoDto.motivoConsulta());
        turno.setEspecialista(especialista);
        turnoRepository.persist(turno);
        return turnoDto;
    }



    @Override
    public List<TurnoDto> getAllTurnosByUsername(String username) {
        return turnoRepository.findAllByUsername(username).stream().map(TurnoMapper::EntityToDto).toList();

    }

    @Override
    @Transactional
    public void deleteTurno(Long id) {
        var turno =  turnoRepository.findByIdOptional(id)
                .orElseThrow(() -> new TurnoException("No se encontro el turno"));
        turnoRepository.delete(turno);
    }
}


