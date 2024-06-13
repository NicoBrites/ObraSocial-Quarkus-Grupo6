package quarkus.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import quarkus.dto.RecetaDto;
import quarkus.dto.RecetaRequest;
import quarkus.dto.mapper.RecetaMapper;
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

    @Override
    public RecetaDto getReceta(Long idTurno){
        var turno = turnoRepository.findByIdOptional(idTurno)
                .orElseThrow(() -> new NotFoundException("El id del Turno es incorrecto"));

        if (!turno.getPaciente().getUsername().toString().equals(jwt.getClaim("upn").toString()))  {
            throw new RecetaException("El id del turno no pertenece a tu Usuario");
        }

        var receta = recetaRepository.findByIdTurno(idTurno)
                .orElseThrow(() -> new NotFoundException("El turno no tiene la receta cargada"));

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
        
        recetaRepository.persist(entityReceta);
        return recetaMapper.EntityToDto(entityReceta);
    }

    @Override
	@Transactional
    public void delete(Long id) {		        
        var receta =  recetaRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Receta no encontrada"));

        receta.setEstaBorrado(true);
        recetaRepository.persist(receta);
    }

    @Override
    @Transactional
    public RecetaDto update(RecetaDto recetaUpdate, Long id) {
        var recetaToUpdate = recetaRepository.findByIdOptional(id)
                .orElseThrow(() -> new RecetaException("Receta no encontrada"));

        if (recetaToUpdate.getEstaBorrado()){ // valido que no este borrada
            throw new RecetaException("Receta no encontrada");
        }   
        Optional<Receta> recetaOpcional = recetaRepository.findByIdTurno(recetaUpdate.turnoId());
        if (recetaOpcional.isPresent()) {

            Receta entity = recetaOpcional.get(); 
            if (entity.id != id) // valido que el idturno de la actualizacion de receta 
            {                    // coincida con el id turno de la receta a actualizar
            throw new RecetaException("Ese turno ya tiene una receta creada");
            }
        }     
       
        if (recetaUpdate.turnoId() != null) {
            recetaToUpdate.setTurno(recetaOpcional.get().getTurno());
        }
        if (recetaUpdate.receta() != null) {
            recetaToUpdate.setReceta(recetaUpdate.receta());
        }
        if (recetaUpdate.fechaCreacion() != null) {
            recetaToUpdate.setFechaCreacion(recetaUpdate.fechaCreacion());
        }
        if (recetaUpdate.fechaValidez() != null) {
            recetaToUpdate.setFechaValidez(recetaUpdate.fechaValidez());
        }

        recetaRepository.persist(recetaToUpdate);
        return recetaMapper.EntityToDto(recetaToUpdate);
    }  
}
