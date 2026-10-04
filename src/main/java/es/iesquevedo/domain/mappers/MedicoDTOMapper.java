package es.iesquevedo.domain.mappers;


import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.domain.dto.MedicoDTO;


public class MedicoDTOMapper {
    public MedicoDTO toDTO(Medico medico){
        if (medico == null) return null;
        return MedicoDTO.builder()
                .medicoId(medico.getMedicoId())
                .nombre(medico.getNombre())
                .especialidad(medico.getEspecialidad())
                .telefono(medico.getTelefono())
                .build();
    }

    public Medico toEntity(MedicoDTO dto){
        if (dto == null) return null;
        return Medico.builder()
                .medicoId(dto.getMedicoId())
                .nombre(dto.getNombre())
                .especialidad(dto.getEspecialidad())
                .telefono(dto.getTelefono())
                .build();

    }

}
