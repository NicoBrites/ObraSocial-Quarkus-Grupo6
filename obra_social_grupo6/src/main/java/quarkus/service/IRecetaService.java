package quarkus.service;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;

public interface IRecetaService {

    RecetaDto getReceta(Long idTurno);

    RecetaDto save(RecetaRequest receta);

    void delete(Long id);

    RecetaDto update(RecetaDto recetaDto, Long id);
}
