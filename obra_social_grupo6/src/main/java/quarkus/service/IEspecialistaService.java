package quarkus.service;

import java.util.List;

import quarkus.dto.EspecialistaDto;

public interface IEspecialistaService {
	
	List<EspecialistaDto> getCartilla();
}
