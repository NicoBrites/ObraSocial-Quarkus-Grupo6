package quarkus.service;

import java.util.List;
import java.util.Optional;


import quarkus.dto.EspecialistaDto;
import quarkus.entity.Especialista;

public interface IEspecialistaService {
	
	List<EspecialistaDto> getCartilla();

	Optional<Especialista> getByID(Long id);

	void delete(Long id);
}
