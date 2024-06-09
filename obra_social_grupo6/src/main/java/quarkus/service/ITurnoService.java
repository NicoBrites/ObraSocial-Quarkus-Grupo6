package quarkus.service;

import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;
import quarkus.entity.Especialista;
import quarkus.entity.Usuario;

import java.util.List;

public interface ITurnoService {

    TurnoDto createTurno(TurnoRequest Turno);

    void validarFechaYHora(Especialista especialista, Usuario paciente, TurnoRequest turnoRequest);

    TurnoRequest updateTurno(TurnoRequest TurnoRequest, Long id);

    List<TurnoDto> getAllByUserId(Long id);

    void deleteTurno(Long id);
}
