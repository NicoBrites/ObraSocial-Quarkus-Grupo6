package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotFoundException;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;
import quarkus.dto.RecetaRequestUpdate;
import quarkus.dto.mapper.RecetaMapper;
import quarkus.entity.Receta;
import quarkus.exception.RecetaException;
import quarkus.exception.TurnoException;
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

    @Override
    public RecetaDto getReceta(Long idTurno){
        var turno = turnoRepository.findByIdOptional(idTurno)
                .orElseThrow(() -> new NotFoundException());

        if (!turno.getPaciente().getUsername().toString().equals(jwt.getClaim("upn").toString()))  {
            throw new RecetaException("El id del turno no pertenece a tu Usuario");
        }

        var receta = recetaRepository.findByIdTurno(idTurno)
                .orElseThrow(() -> new NotFoundException());

        if (receta.getFechaValidez().isBefore(LocalDate.now())){
            throw new RecetaException("Ya se vencio la fecha de validez de la Receta");
        }

        return recetaMapper.EntityToDto(receta);
    }
 
    @Override
    @Transactional
    public RecetaDto save(RecetaRequest recetaRequest) {
        var turno = turnoRepository.findByIdOptional(recetaRequest.turnoId())
                .orElseThrow(() -> new TurnoException("El id del Turno es incorrecto"));

        Optional<Receta> recetaOpcional = recetaRepository.findByIdTurno(recetaRequest.turnoId());
        if (recetaOpcional.isPresent()) {
            throw new TurnoException("Ese turno ya tiene una receta creada");
        } 

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaValidez = fechaCreacion.plusDays(30);

        Receta entityReceta = recetaMapper.RequestToEntity(recetaRequest, turno, fechaCreacion, fechaValidez);
        entityReceta.setEstaBorrado(false);
        
        try{
            recetaRepository.persist(entityReceta);
            return recetaMapper.EntityToDto(entityReceta);
        } catch ( Exception e)
        {
            throw new InternalServerErrorException();
        }
    }

    @Override
	@Transactional
    public void delete(Long id) {		        
        var receta =  recetaRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException());

        try {
            receta.setEstaBorrado(true);
            recetaRepository.persist(receta);
        } catch ( Exception e)
        {
            throw new InternalServerErrorException();
        }
    }

    @Override
    @Transactional
    public RecetaDto update(RecetaRequestUpdate recetaRequestUpdate, Long id) {
        var recetaToUpdate = recetaRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException());

        if (recetaToUpdate.getEstaBorrado()){ // valido que no este borrada
            throw new NotFoundException();
        }   
        Optional<Receta> recetaOpcional = recetaRepository.findByIdTurno(recetaRequestUpdate.turnoId());
        if (recetaOpcional.isPresent()) {

            Receta entity = recetaOpcional.get(); 
            if (entity.id != id) // valido que el idturno de la actualizacion de receta 
            {                    // coincida con el id turno de la receta a actualizar
            throw new NotAcceptableException("El id de turno que envio ya tiene una receta creada");
            }
        }    

        var turno = turnoRepository.findByIdOptional(recetaRequestUpdate.turnoId())
                .orElseThrow(() -> new TurnoException("El id del Turno es incorrecto")); 
        
        ValidadorFechas(recetaRequestUpdate.fechaCreacion(), recetaRequestUpdate.fechaValidez());
        
        recetaToUpdate.setTurno(turno);
        recetaToUpdate.setReceta(recetaRequestUpdate.receta());
        recetaToUpdate.setFechaCreacion(recetaRequestUpdate.fechaCreacion());
        recetaToUpdate.setFechaValidez(recetaRequestUpdate.fechaValidez());
        
        try {
            recetaRepository.persist(recetaToUpdate);
            return recetaMapper.EntityToDto(recetaToUpdate);
        } catch ( Exception e)
        {
            throw new InternalServerErrorException();
        }
    }  


    private void ValidadorFechas(LocalDate fechaCreacion, LocalDate fechaValidez){

        if (fechaCreacion.isAfter(LocalDate.now()))
        {
            throw new TurnoException("La fecha de creacion no puede ser en el futuro");
        }
        if (fechaValidez.isBefore(fechaCreacion))
        {
            throw new TurnoException("La fecha limite de validez no puede ser antes de la fecha de creacion de la receta");
        }

    }
}
