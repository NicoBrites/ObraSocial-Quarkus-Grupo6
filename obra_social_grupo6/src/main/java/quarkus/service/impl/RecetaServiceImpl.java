package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;
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

    @Override
    public RecetaDto getReceta(Long idTurno){

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
    public RecetaDto save(RecetaRequest recetaRequest) {
        Optional<Turno> turnoOpcional = turnoRepository.findByIdOptional(recetaRequest.turnoId());
        if (turnoOpcional.isEmpty()) {
            throw new UserNotFoundException("Turno no encontrado");
        }
        Optional<Receta> recetaOpcional = recetaRepository.findByIdTurno(recetaRequest.turnoId());
        if (recetaOpcional.isPresent()) {
            throw new RecetaException("Ese turno ya tiene una receta creada");
        }
        Turno entityTurno = turnoOpcional.get(); 

        LocalDate fechaCreacion = LocalDate.now();
        LocalDate fechaValidez = fechaCreacion.plusDays(30);

        Receta entityReceta = recetaMapper.RequestToEntity(recetaRequest, entityTurno, fechaCreacion, fechaValidez);
        entityReceta.setEstaBorrado(false);
        
        recetaRepository.persist(entityReceta);
        return recetaMapper.EntityToDto(entityReceta);
    }

    @Override
	@Transactional
    public void delete(Long id) {		        
        Optional<Receta> optional = Receta.findByIdOptional(id);
        if (optional.isEmpty()) {
            throw new RecetaException("Receta no encontrada");
        }
        Receta entity = optional.get();   
        entity.setEstaBorrado(true);
        recetaRepository.persist(entity);     
    }

    @Override
    @Transactional
    public RecetaDto update(RecetaDto recetaUpdate, Long id) {
        Optional<Receta> optional = Receta.findByIdOptional(id);
        if (optional.isEmpty()) { // valido que exista la receta
            throw new RecetaException("Receta no encontrada");
        }
        Receta entityToUpdate = optional.get(); 
        if (entityToUpdate.getEstaBorrado()){ // valido que no este borrada
            throw new RecetaException("Receta no encontrada");
        }   
        Optional<Receta> recetaOpcional = recetaRepository.findByIdTurno(recetaUpdate.turnoId());
        if (recetaOpcional.isPresent()) {

            Receta entity = optional.get(); 
            if (entity.id != id) // valido que el idturno de la actualizacion de receta 
            {                    // coincida con el id turno de la receta a actualizar
            throw new RecetaException("Ese turno ya tiene una receta creada");
            }
        }     
       
        if (recetaUpdate.turnoId() != null) {
            entityToUpdate.setTurno(recetaOpcional.get().getTurno());
        }
        if (recetaUpdate.receta() != null) {
            entityToUpdate.setReceta(recetaUpdate.receta());
        }
        if (recetaUpdate.fechaCreacion() != null) {
            entityToUpdate.setFechaCreacion(recetaUpdate.fechaCreacion());
        }
        if (recetaUpdate.fechaValidez() != null) {
            entityToUpdate.setFechaValidez(recetaUpdate.fechaValidez());
        }

        recetaRepository.persist(entityToUpdate);
        return recetaMapper.EntityToDto(entityToUpdate);
    }  
}
