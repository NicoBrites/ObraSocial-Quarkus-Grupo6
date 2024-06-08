package quarkus.dto.mapper;

import quarkus.dto.TurnoRequest;
import quarkus.dto.TurnoDto;
import quarkus.entity.Especialista;
import quarkus.entity.Turno;
import quarkus.entity.Usuario;

public class TurnoMapper {

    public static Turno requestToEntity(TurnoRequest turnoRequest, Usuario usuario, Especialista especialista){
        return Turno.builder()
                .fecha(turnoRequest.fecha())
                .hora(turnoRequest.hora())
                .paciente(usuario)
                .especialista(especialista)
                .motivoConsulta(turnoRequest.motivoConsulta())
                .build();
    }


    public  static TurnoDto EntityToDto (Turno turno) {
        return new TurnoDto(turno.id,turno.getPaciente().id,turno.getEspecialista().id,turno.getFecha(),turno.getHora(),turno.getMotivoConsulta(),turno.getEstado());
    }
}
