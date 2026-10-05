package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.Medico;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository {
    List<Medico>findAll();
    Optional<Medico>findById(Long id);
}
