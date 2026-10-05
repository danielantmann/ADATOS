package es.iesquevedo.domain.mappers;

import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.domain.dto.HistoriaClinicaDTO;

public class HistoriaClinicaDTOMapper {

    public HistoriaClinicaDTO toDto(HistoriaClinica historiaClinica) {
        if (historiaClinica == null) return null;
        return HistoriaClinicaDTO.builder()
                .historiaId(historiaClinica.getHistoriaId())
                .pacienteId(historiaClinica.getPacienteId())
                .medicoId(historiaClinica.getMedicoId())
                .diagnostico(historiaClinica.getDiagnostico())
                .fechaAdmision(historiaClinica.getFechaAdmision())
                .build();
    }

    public HistoriaClinica toEntity(HistoriaClinicaDTO dto) {
        if (dto == null) return null;
        return HistoriaClinica.builder()
                .historiaId(dto.getHistoriaId())
                .pacienteId(dto.getPacienteId())
                .medicoId(dto.getMedicoId())
                .diagnostico(dto.getDiagnostico())
                .fechaAdmision(dto.getFechaAdmision())
                .build();
    }
}