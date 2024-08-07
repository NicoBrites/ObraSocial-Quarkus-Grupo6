package quarkus.service;

import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;
import quarkus.entity.Especialista;
import quarkus.entity.Usuario;

import java.util.List;

public interface ITurnoService {

    TurnoDto createTurno(TurnoRequest Turno);

    void validarFechaYHora(Especialista especialista, Usuario paciente, TurnoRequest turnoRequest);

    TurnoDto updateTurno(TurnoRequest TurnoRequest, Long id);

    List<TurnoDto> getAllByUser();

    void deleteTurno(Long id);
}
