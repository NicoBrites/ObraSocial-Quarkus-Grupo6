package quarkus.dto.mapper;

import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.EspecialistaDto;
import quarkus.entity.Especialista;

@RequestScoped
public class EspecialistaMapper implements IEspecialistaMapper {

	@Override
	public EspecialistaDto EntityToDto(Especialista especialista) {
			
		return new EspecialistaDto(especialista.getNombre(),
				especialista.getEspecialidad(),
				especialista.getHorarioEntrada(),
				especialista.getHorarioSalida(),
				especialista.getUbicacion()); 
	}

}

