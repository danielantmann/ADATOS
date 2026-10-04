package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoDTO {
    private Long medicoId;
    private String nombre;
    private String especialidad;
    private String telefono;

}
