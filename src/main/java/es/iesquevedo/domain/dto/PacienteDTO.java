package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {
    private Long pacienteId;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String telefono;
}