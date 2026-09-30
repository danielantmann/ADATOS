package es.iesquevedo.dao.model;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
  private Long id;
  private String username;
  private String password;
  private Long pacienteId;
  private Long medicoId;
}
