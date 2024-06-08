package quarkus.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import quarkus.dto.EspecialistaDto;
import quarkus.dto.mapper.EspecialistaMapper;
import quarkus.repository.EspecialistaRepository;
import quarkus.service.IEspecialistaService;

@ApplicationScoped
public class EspecialistaServiceImpl implements IEspecialistaService {

	@Inject
    private EspecialistaRepository especialistaRepository;
	@Inject
    private EspecialistaMapper especialistaMapper;

	@Override
	public List<EspecialistaDto> getCartilla() {
		return especialistaRepository.findAll().stream()
				.map(especialistaMapper::EntityToDto)
				.collect(Collectors.toList());			
	}

}
