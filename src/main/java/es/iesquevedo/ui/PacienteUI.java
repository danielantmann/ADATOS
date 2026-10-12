package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.services.PacienteService;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class PacienteUI {
    private final PacienteService pacienteService;

    @Inject
    public PacienteUI(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    public void mostrarMenuPacientes() {
        int opcion = -1;
        do {
            IO.println("\n--- GESTIÓN DE PACIENTES ---");
            IO.println("1. Mostrar todos los pacientes");
            IO.println("2. Añadir paciente");
            IO.println("3. Actualizar paciente");
            IO.println("4. Borrar paciente");
            IO.println("0. Volver al menú principal");
            IO.println("Elija una opción: ");

            String input = IO.readln();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                IO.println("Por favor, introduzca un número válido.");
                continue;
            }

            switch (opcion) {
                case 1: listarPacientes(); break;
                case 2: anadirPaciente(); break;
                case 3: actualizarPaciente(); break;
                case 4: borrarPaciente(); break;
                case 0: IO.println("Volviendo..."); break;
                default: IO.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void listarPacientes() {
        List<PacienteDTO> lista = pacienteService.getAllPacientes();
        if (lista.isEmpty()) {
            IO.println("No hay pacientes registrados.");
        } else {
            IO.println("\n--- LISTA DE PACIENTES ---");
            for (PacienteDTO p : lista) {
                IO.println("ID: " + p.getPacienteId() +
                        " | Nombre: " + p.getNombre() +
                        " | F. Nacimiento: " + p.getFechaNacimiento() +
                        " | Teléfono: " + p.getTelefono());
            }
        }
    }

    private void anadirPaciente() {
        try {
            IO.println("Introduce el nombre del paciente: ");
            String nombre = IO.readln();

            IO.println("Introduce la fecha de nacimiento (YYYY-MM-DD): ");
            String fechaStr = IO.readln();
            LocalDate fechaNac = (fechaStr == null || fechaStr.trim().isEmpty()) ? null : LocalDate.parse(fechaStr.trim());

            IO.println("Introduce el teléfono: ");
            String telefono = IO.readln();

            PacienteDTO nuevo = PacienteDTO.builder()
                    .nombre(nombre)
                    .fechaNacimiento(fechaNac)
                    .telefono(telefono)
                    .build();

            Long nuevoId = pacienteService.savePaciente(nuevo);
            if (nuevoId != null) {
                IO.println("¡Paciente añadido con éxito! ID asignado: " + nuevoId);
            } else {
                IO.println("Error al añadir el paciente.");
            }
        } catch (DateTimeParseException e) {
            IO.println("Error: Formato de fecha incorrecto. Debe usar el formato YYYY-MM-DD (ej: 2000-05-12).");
        }
    }

    private void actualizarPaciente() {
        try {
            IO.println("Introduce el ID del paciente a actualizar: ");
            Long id = Long.parseLong(IO.readln());

            Optional<PacienteDTO> existente = pacienteService.getPacienteById(id);
            if (existente.isEmpty()) {
                IO.println("Error: No existe ningún paciente con el ID " + id + ".");
                return;
            }

            IO.println("Nuevo nombre: ");
            String nombre = IO.readln();

            IO.println("Nueva fecha de nacimiento (YYYY-MM-DD): ");
            String fechaStr = IO.readln();
            LocalDate fechaNac = (fechaStr == null || fechaStr.trim().isEmpty()) ? null : LocalDate.parse(fechaStr.trim());

            IO.println("Nuevo teléfono: ");
            String telefono = IO.readln();

            PacienteDTO actualizado = PacienteDTO.builder()
                    .pacienteId(id)
                    .nombre(nombre)
                    .fechaNacimiento(fechaNac)
                    .telefono(telefono)
                    .build();

            boolean exito = pacienteService.updatePaciente(actualizado);
            if (exito) {
                IO.println("¡Paciente actualizado con éxito!");
            } else {
                IO.println("Error al actualizar el paciente.");
            }
        } catch (NumberFormatException e) {
            IO.println("Por favor, introduzca un ID numérico válido.");
        } catch (DateTimeParseException e) {
            IO.println("Error: Formato de fecha incorrecto. Debe usar el formato YYYY-MM-DD (ej: 2000-05-12).");
        }
    }

    private void borrarPaciente() {
        IO.println("Introduce el ID del paciente a borrar: ");
        try {
            Long id = Long.parseLong(IO.readln());

            Optional<PacienteDTO> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isEmpty()) {
                IO.println("Error: No existe ningún paciente con el ID " + id + ".");
                return;
            }

            IO.println("¿Estás seguro de que deseas borrar al paciente: " + pacienteOpt.get().getNombre() + "? (s/n): ");
            String confirmacion = IO.readln();

            if (confirmacion != null && confirmacion.equalsIgnoreCase("s")) {
                boolean exito = pacienteService.deletePaciente(id);
                if (exito) {
                    IO.println("¡Paciente borrado con éxito!");
                } else {
                    IO.println("No se pudo borrar el paciente. Probablemente tenga un usuario o historia clínica asociada.");
                }
            } else {
                IO.println("Operación de borrado cancelada.");
            }

        } catch (NumberFormatException e) {
            IO.println("Por favor, introduzca un ID numérico válido.");
        }
    }
}