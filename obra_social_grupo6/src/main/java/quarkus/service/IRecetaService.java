package quarkus.service;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;
import quarkus.dto.RecetaRequestUpdate;

public interface IRecetaService {

    RecetaDto getReceta(Long idTurno);

    RecetaDto save(RecetaRequest receta);

    void delete(Long id);

    RecetaDto update(RecetaRequestUpdate recetaUpdate, Long id);
}
