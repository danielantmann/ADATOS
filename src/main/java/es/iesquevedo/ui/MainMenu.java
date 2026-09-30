package es.iesquevedo.ui;

import jakarta.inject.Inject;

public class MainMenu {

  private final UsuarioUI usuarioUi;

  @Inject
  public MainMenu(UsuarioUI usuarioUi) {
    this.usuarioUi = usuarioUi;
  }

  public void run() {
    try {
      IO.println("Hospital App");

      usuarioUi.login();

    } catch (Exception e) { // Solo errores críticos no manejados
      System.err.println("Fallo grave: " + e.getMessage());
      System.exit(1);
    }


  }

}
