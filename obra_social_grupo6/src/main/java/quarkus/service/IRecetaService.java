package quarkus.service;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;

public interface IRecetaService {

    RecetaDto getReceta(Long idTurno, String authorizationHeader);

    RecetaDto save(RecetaRequest receta);
}
