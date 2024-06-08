package quarkus.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.entity.Especialista;

@ApplicationScoped
public class EspecialistaRepository implements PanacheRepository<Especialista>{

}

