package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import quarkus.dto.RecetaDto;
import quarkus.dto.mapper.RecetaMapper;
import quarkus.entity.Turno;
import quarkus.entity.Receta;
import quarkus.exception.RecetaException;
import quarkus.exception.TurnoException;
import quarkus.repository.RecetaRepository;
import quarkus.repository.TurnoRepository;
import quarkus.service.IRecetaService;

import java.time.LocalDate;
import java.util.Optional;

import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class RecetaServiceImpl  implements IRecetaService{

    @Inject
    private RecetaRepository recetaRepository;
    @Inject
    private TurnoRepository turnoRepository;
	@Inject
    private RecetaMapper recetaMapper;
    @Inject
    private JsonWebToken jwt;

    public RecetaDto getReceta(Long idTurno, String Authorization){

        Optional<Turno> turnoOpcional = turnoRepository.findByIdOptional(idTurno);
        if (turnoOpcional.isEmpty()) {
            throw new TurnoException("Turno no encontrado");
        }
        Turno entity = turnoOpcional.get(); 

        if (!entity.getPaciente().getUsername().toString().equals(jwt.getClaim("upn").toString()))  {
            throw new RecetaException("A este Paciente no le corresponde la receta de este Turno");
        }
        Optional<Receta> recetaOpcion = recetaRepository.findByIdTurno(idTurno);

        Receta receta =  recetaOpcion.get();
        if (receta.getFechaValidez().isBefore(LocalDate.now())){
            throw new RecetaException("Ya se vencio la fecha de validez de la Receta");
        }

        return recetaMapper.EntityToDto(receta);
    }
}
