package quarkus.dto.mapper;

import java.time.LocalDate;

import quarkus.dto.EspecialistaDto;
import quarkus.dto.RecetaDto;
import quarkus.entity.Especialista;
import quarkus.entity.Receta;

public class RecetaMapper {

    public RecetaDto EntityToDto(Receta receta) {
			
		return new RecetaDto(receta.getTurno().id,
                receta.getTurno().getEspecialista().getNombre(),
				receta.getTurno().getEspecialista().getEspecialidad(),
				receta.getReceta(),
				receta.getFechaCreacion()); 
	}

}
