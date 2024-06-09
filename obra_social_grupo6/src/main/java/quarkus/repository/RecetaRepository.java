package quarkus.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.entity.Receta;

@ApplicationScoped
public class RecetaRepository implements PanacheRepository<Receta> {

    public Receta findByIdTurno(Long idTurno) {
        return find("turno.id_turno", idTurno).firstResult();
    }
}
