package quarkus.dto.mapper;

import java.time.LocalDate;

import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.RecetaDto;
import quarkus.entity.Receta;
import quarkus.entity.Turno;

@RequestScoped
public class RecetaMapper {

    public RecetaDto EntityToDto(Receta receta) {
			
		return new RecetaDto(receta.getTurno().id,
                receta.getTurno().getEspecialista().getNombre(),
				receta.getTurno().getEspecialista().getEspecialidad(),
				receta.getReceta(),
				receta.getFechaCreacion()); 
	}

	public Receta DtoToEntity(RecetaDto recetaDto, Turno turno, LocalDate fechaValidez) {
		 return Receta.builder()
				.turno(turno)
				.receta(recetaDto.receta())
				.fechaCreacion(recetaDto.fechaCreacion())
				.fechaValidez(fechaValidez)
				.build();
    }

}
