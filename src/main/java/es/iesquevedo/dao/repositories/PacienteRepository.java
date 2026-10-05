package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteRepository {
    List<Paciente> findAll();
    Optional<Paciente> findById(Long id);
    boolean save(Paciente paciente);
    boolean update(Paciente paciente);
    boolean delete(Long id);
}
