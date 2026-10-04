package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.HistoriaClinica;

import java.util.List;
import java.util.Optional;

public interface HistoriaClinicaRepository {
    List<HistoriaClinica> findByPacienteId(Long pacienteId);
    Optional<HistoriaClinica> findById(Long historiaId);
    boolean save (HistoriaClinica historia);
    boolean update (HistoriaClinica historia);
    boolean delete(Long historiaId);
}
