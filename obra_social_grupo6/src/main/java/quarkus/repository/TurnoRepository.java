package quarkus.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.entity.Turno;

import java.util.List;

@ApplicationScoped
public class TurnoRepository implements PanacheRepository<Turno> {

    public List<Turno> findAllByUserId(Long id) {
        return find("paciente.id", id).list();
    }
}
