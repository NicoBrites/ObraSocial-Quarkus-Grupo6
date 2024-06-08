package quarkus.service;

import jakarta.transaction.Transactional;
import quarkus.dto.TurnoDto;
import quarkus.dto.TurnoRequest;

import java.util.List;

public interface ITurnoService {

    TurnoDto createTurno(TurnoRequest Turno);

    TurnoDto updateTurno(TurnoDto turnoDto, Long id);

    List<TurnoDto> getAllTurnosByUsername(String username);
    void deleteTurno(Long id);
}
