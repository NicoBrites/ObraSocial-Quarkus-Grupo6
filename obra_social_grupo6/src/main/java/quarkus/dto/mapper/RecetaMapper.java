package quarkus.dto.mapper;


import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.RecetaDto;
import quarkus.entity.Receta;

@RequestScoped
public class RecetaMapper {

    public RecetaDto EntityToDto(Receta receta) {
			
		return new RecetaDto(receta.getTurno().id,
                receta.getTurno().getEspecialista().getNombre(),
				receta.getTurno().getEspecialista().getEspecialidad(),
				receta.getReceta(),
				receta.getFechaCreacion()); 
	}

}
