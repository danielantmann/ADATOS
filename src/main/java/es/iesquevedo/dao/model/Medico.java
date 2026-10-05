package es.iesquevedo.dao.model;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Medico {
    private Long medicoId;
    private String nombre;
    private String especialidad;
    private String telefono;
}
