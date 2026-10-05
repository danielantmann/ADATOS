package es.iesquevedo.domain.mappers;

import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.domain.dto.PacienteDTO;

public class PacienteDTOMapper {
    public PacienteDTO toDTO(Paciente paciente) {
        if (paciente == null) return null;
        return PacienteDTO.builder()
                .pacienteId(paciente.getPacienteId())
                .nombre(paciente.getNombre())
                .fechaNacimiento(paciente.getFechaNacimiento())
                .telefono(paciente.getTelefono())
                .build();
    }

    public Paciente toEntity(PacienteDTO dto) {
        if (dto == null) return null;
        return Paciente.builder()
                .pacienteId(dto.getPacienteId())
                .nombre(dto.getNombre())
                .fechaNacimiento(dto.getFechaNacimiento())
                .telefono(dto.getTelefono())
                .build();
    }
}