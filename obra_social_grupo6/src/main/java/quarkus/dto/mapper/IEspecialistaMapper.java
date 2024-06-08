package quarkus.dto.mapper;

import quarkus.dto.CartillaDto;
import quarkus.entity.Especialista;

public interface IEspecialistaMapper {

	CartillaDto CartillaMap(Especialista especialista);
}
