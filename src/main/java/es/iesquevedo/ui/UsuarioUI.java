package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.UsuarioDTO;
import es.iesquevedo.domain.services.UsuarioService;
import jakarta.inject.Inject;

public class UsuarioUI {
  private final UsuarioService usuarioService;

  @Inject
  public UsuarioUI(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  public void login() {
    IO.println("Por favor, introduzca sus credenciales");

    while (true) {
      IO.println("Usuario: ");
      String username = IO.readln();
      if (username.isEmpty()) continue;

      IO.println("Contraseña: ");
      String password = IO.readln();
      if (password.isEmpty()) continue;
      UsuarioDTO credenciales = new UsuarioDTO(username, password);

      boolean ok = usuarioService.login(credenciales);
      if (ok) {
        IO.println("Bienvenido al sistema.");
        break;
      } else {
        IO.println("Credenciales incorrectas, inténtelo de nuevo.");
      }
    }
  }
}