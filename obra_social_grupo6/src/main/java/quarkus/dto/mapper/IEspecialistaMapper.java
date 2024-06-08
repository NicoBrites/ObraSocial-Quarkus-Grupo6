package quarkus.dto.mapper;

import quarkus.dto.EspecialistaDto;
import quarkus.entity.Especialista;

public interface IEspecialistaMapper {

	EspecialistaDto EntityToDto(Especialista especialista);
}
