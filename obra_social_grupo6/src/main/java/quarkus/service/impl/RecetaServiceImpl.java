package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import quarkus.dto.RecetaDto;
import quarkus.dto.mapper.RecetaMapper;
import quarkus.entity.Turno;
import quarkus.entity.Receta;
import quarkus.exception.RecetaException;
import quarkus.exception.UserNotFoundException;
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
            throw new UserNotFoundException("Turno no encontrado");
        }
        Turno entity = turnoOpcional.get(); 

        if (!entity.getPaciente().getUsername().toString().equals(jwt.getClaim("upn").toString()))  {
            throw new RecetaException("El id del turno no pertenece a tu Usuario");
        }
        Optional<Receta> recetaOpcion = recetaRepository.findByIdTurno(idTurno);

        Receta receta =  recetaOpcion.get();
        if (receta.getFechaValidez().isBefore(LocalDate.now())){
            throw new RecetaException("Ya se vencio la fecha de validez de la Receta");
        }

        return recetaMapper.EntityToDto(receta);
    }
 
    @Override
    @Transactional
    public RecetaDto save(RecetaDto recetaDto) {
        Optional<Turno> turnoOpcional = turnoRepository.findByIdOptional(recetaDto.turnoId());
        if (turnoOpcional.isEmpty()) {
            throw new UserNotFoundException("Turno no encontrado");
        }
        Turno entityTurno = turnoOpcional.get(); 

        LocalDate fechaValidez = LocalDate.now().plusDays(30);

        Receta entityReceta = recetaMapper.DtoToEntity(recetaDto,entityTurno, fechaValidez );

        recetaRepository.persist(entityReceta);
        return recetaMapper.EntityToDto(entityReceta);
    }
}
