package quarkus.service.impl;

import java.util.List;
import java.util.stream.Collectors;


import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import quarkus.dto.EspecialistaDto;
import quarkus.dto.EspecialistaRequest;
import quarkus.dto.mapper.EspecialistaMapper;
import quarkus.entity.Especialista;
import quarkus.exception.UserNotFoundException;
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
				.filter(especialista -> !especialista.getEstaBorrado() )
				.map(especialistaMapper::EntityToDto)
				.collect(Collectors.toList());			
	}

	@Override
	public Optional<Especialista> getByID(Long especialistaId) {
        return especialistaRepository.findByIdOptional(especialistaId);
    }	

	@Override
	@Transactional
    public void delete(Long id) {		        
        Optional<Especialista> optional = Especialista.findByIdOptional(id);
        if (optional.isEmpty()) {
            throw new UserNotFoundException("Especialista no encontrado");
        }
        Especialista entity = optional.get();   
        entity.setEstaBorrado(true);
        especialistaRepository.persist(entity);     
    }

    @Override
    @Transactional
    public EspecialistaDto save(EspecialistaRequest especialistaRequest) {
        Especialista entity = especialistaMapper.RequestToEntity(especialistaRequest);
        especialistaRepository.persist(entity);
        return especialistaMapper.EntityToDto(entity);
    }
	

}
