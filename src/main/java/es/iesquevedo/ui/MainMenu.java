package es.iesquevedo.ui;

import jakarta.inject.Inject;

public class MainMenu {

  private final UsuarioUI usuarioUi;
  private final PacienteUI pacienteUi;
  private final MedicoUI medicoUI;
  private final HistoriaClinicaUI historiaClinicaUI; // <-- Anadido

  @Inject
  public MainMenu(UsuarioUI usuarioUi, PacienteUI pacienteUi, MedicoUI medicoUI, HistoriaClinicaUI historiaClinicaUI) {
    this.usuarioUi = usuarioUi;
    this.pacienteUi = pacienteUi;
    this.medicoUI = medicoUI;
    this.historiaClinicaUI = historiaClinicaUI; // <-- Asignado
  }

  public void run() {
    try {
      IO.println("Hospital App");

      if (usuarioUi.login()) {
        mostrarMenuPrincipal();
      }

    } catch (Exception e) {
      System.err.println("Fallo grave: " + e.getMessage());
      System.exit(1);
    }
  }

  private void mostrarMenuPrincipal() {
    int opcion = -1;
    do {
      IO.println("\n===== MENU PRINCIPAL - HOSPITAL =====");
      IO.println("1. Gestion de Pacientes");
      IO.println("2. Gestion de Medicos");
      IO.println("3. Gestion de Historia Clinica"); // <-- Opcion nueva
      IO.println("0. Salir");
      IO.println("Elija una opcion: ");

      String input = IO.readln();
      try {
        opcion = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        IO.println("Por favor, introduzca un numero valido.");
        continue;
      }

      switch (opcion) {
        case 1:
          pacienteUi.mostrarMenuPacientes();
          break;
        case 2:
          medicoUI.mostrarMenuMedicos();
          break;
        case 3:
          historiaClinicaUI.mostrarMenuHistoria(); // <-- Redirige al menu de historia
          break;
        case 0:
          IO.println("Saliendo de la aplicacion. ¡Hasta pronto!");
          break;
        default:
          IO.println("Opcion no valida.");
      }
    } while (opcion != 0);
  }
}