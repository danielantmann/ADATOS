package es.iesquevedo.domain.services;

import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.repositories.HistoriaClinicaRepository;
import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.mappers.HistoriaClinicaDTOMapper;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HistoriaClinicaService {
    private final HistoriaClinicaRepository repository;
    private final HistoriaClinicaDTOMapper mapper;

    @Inject
    public HistoriaClinicaService(HistoriaClinicaRepository repository, HistoriaClinicaDTOMapper mapper) {
        this.repository = repository;
        this.mapper = new HistoriaClinicaDTOMapper();
    }

    public List<HistoriaClinicaDTO> getHistoriasByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<HistoriaClinicaDTO> getHistoriaById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public boolean saveHistoria(HistoriaClinicaDTO dto) {
        HistoriaClinica historia = mapper.toEntity(dto);
        return repository.save(historia);
    }

    public boolean updateHistoria(HistoriaClinicaDTO dto) {
        HistoriaClinica historia = mapper.toEntity(dto);
        return repository.update(historia);
    }

    public boolean deleteHistoria(Long id) {
        return repository.delete(id);
    }
}