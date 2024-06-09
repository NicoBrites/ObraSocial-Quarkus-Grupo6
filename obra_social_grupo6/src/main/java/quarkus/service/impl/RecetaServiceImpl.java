package quarkus.service.impl;

import jakarta.inject.Inject;
import quarkus.dto.RecetaDto;
import quarkus.dto.mapper.EspecialistaMapper;
import quarkus.repository.RecetaRepository;
import quarkus.service.IRecetaService;

public class RecetaServiceImpl  implements IRecetaService{

    @Inject
    private RecetaRepository recetaRepository;
	@Inject
    private EspecialistaMapper especialistaMapper;

    public RecetaDto getReceta(){

        return new RecetaDto(null, null, null, null, null);
    }
}
