package quarkus.service;

import java.util.List;

import quarkus.dto.CartillaDto;

public interface IEspecialistaService {
	
	List<CartillaDto> getCartilla();
}
