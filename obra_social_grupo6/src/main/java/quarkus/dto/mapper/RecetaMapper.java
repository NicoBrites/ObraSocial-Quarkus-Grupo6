package quarkus.dto.mapper;

import java.time.LocalDate;

import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;
import quarkus.entity.Receta;
import quarkus.entity.Turno;

@RequestScoped
public class RecetaMapper {

    public RecetaDto EntityToDto(Receta receta) {
			
		return new RecetaDto(receta.getTurno().id,
				receta.getReceta(),
				receta.getFechaCreacion(),
				receta.getFechaValidez());  
	}

	public Receta RequestToEntity(RecetaRequest recetaRequest, Turno turno, LocalDate fechaCreacion, LocalDate fechaValidez){
        return Receta.builder()
				.turno(turno)
				.receta(recetaRequest.receta())
				.fechaCreacion(fechaCreacion)
				.fechaValidez(fechaValidez)
                .build();
    }

	public Receta DtoToEntity(RecetaDto recetaDto, Turno turno){
        return Receta.builder()
				.turno(turno)
				.receta(recetaDto.receta())
				.fechaCreacion(recetaDto.fechaCreacion())
				.fechaValidez(recetaDto.fechaValidez())
                .build();
    }

}
