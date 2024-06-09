package quarkus.service;
import quarkus.dto.RecetaDto;

public interface IRecetaService {

    RecetaDto getReceta(Long idTurno, String authorizationHeader);
}
