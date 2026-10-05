package es.iesquevedo.dao.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Paciente {
    private Long pacienteId;
    private  String nombre;
    private LocalDate fechaNacimiento;
    private String telefono;
}
