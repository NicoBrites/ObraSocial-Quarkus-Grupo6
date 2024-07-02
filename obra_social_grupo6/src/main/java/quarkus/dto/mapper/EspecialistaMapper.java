package quarkus.dto.mapper;

import jakarta.enterprise.context.RequestScoped;
import quarkus.dto.EspecialistaDto;
import quarkus.dto.EspecialistaRequest;
import quarkus.entity.Especialista;


@RequestScoped
public class EspecialistaMapper {

	public EspecialistaDto EntityToDto(Especialista especialista) {			
		return new EspecialistaDto(
				especialista.id,
				especialista.getNombre(),
				especialista.getEspecialidad(),
				especialista.getHorarioEntrada(),
				especialista.getHorarioSalida(),
				especialista.getUbicacion()); 
	}
	
	public Especialista RequestToEntity(EspecialistaRequest especialistaRequest){
        return Especialista.builder()
				.nombre(especialistaRequest.nombre())
                .especialidad(especialistaRequest.especialidad())
                .horarioEntrada(especialistaRequest.horarioEntrada())
                .horarioSalida(especialistaRequest.horarioSalida())
                .ubicacion(especialistaRequest.ubicacion())
                .build();
    }
}

