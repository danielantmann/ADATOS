package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.mappers.PacienteDTOMapper;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PacienteDTOMapper mapper = new PacienteDTOMapper();

    @Inject
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteDTO> getAllPacientes() {
        return pacienteRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Long savePaciente(PacienteDTO pacienteDTO) { // <--- Cambiado a Long
        return pacienteRepository.save(mapper.toEntity(pacienteDTO));
    }

    public boolean updatePaciente(PacienteDTO pacienteDTO) {
        return pacienteRepository.update(mapper.toEntity(pacienteDTO));
    }

    public boolean deletePaciente(Long id) {
        return pacienteRepository.delete(id);
    }

    public java.util.Optional<PacienteDTO> getPacienteById(Long id) {
        return pacienteRepository.findById(id)
                .map(mapper::toDTO);
    }
}