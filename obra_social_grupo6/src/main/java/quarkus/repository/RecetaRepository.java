package quarkus.repository;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.entity.Receta;

@ApplicationScoped
public class RecetaRepository implements PanacheRepository<Receta> {

    public Optional<Receta> findByIdTurno(Long idTurno) {
        return find("turno.id", idTurno).firstResultOptional();
        
    }
}
