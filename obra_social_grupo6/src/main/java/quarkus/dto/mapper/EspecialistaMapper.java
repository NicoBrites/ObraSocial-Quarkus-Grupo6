package quarkus.dto.mapper;

import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.CartillaDto;
import quarkus.entity.Especialista;

@RequestScoped
public class EspecialistaMapper implements IEspecialistaMapper {

	@Override
	public CartillaDto CartillaMap(Especialista especialista) {
			
		return new CartillaDto(especialista.getNombre(),
				especialista.getEspecialidad(),
				especialista.getHorarioEntrada(),
				especialista.getHorarioSalida(),
				especialista.getUbicacion()); 
	}

}

