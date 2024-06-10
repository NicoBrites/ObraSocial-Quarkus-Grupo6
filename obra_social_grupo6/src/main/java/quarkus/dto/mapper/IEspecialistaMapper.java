package quarkus.dto.mapper;

import quarkus.dto.EspecialistaDto;
import quarkus.dto.EspecialistaRequest;
import quarkus.entity.Especialista;

public interface IEspecialistaMapper {

	EspecialistaDto EntityToDto(Especialista especialista);

	Especialista RequestToEntity(EspecialistaRequest especialista);

	Especialista DtoToEntity(EspecialistaDto especialista);
}
