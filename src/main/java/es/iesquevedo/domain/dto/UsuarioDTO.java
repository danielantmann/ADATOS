package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UsuarioDTO {
  private String username;
  private String password;
}