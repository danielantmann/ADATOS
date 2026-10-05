package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.services.HistoriaClinicaService;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class HistoriaClinicaUI {
    private final HistoriaClinicaService service;

    @Inject
    public HistoriaClinicaUI(HistoriaClinicaService service) {
        this.service = service;
    }

    public void mostrarMenuHistoria() {
        int opcion = -1;
        do {
            IO.println("\n--- GESTIÓN DE HISTORIA CLÍNICA ---");
            IO.println("1. Mostrar historia de un paciente");
            IO.println("2. Añadir registro a la historia");
            IO.println("3. Actualizar registro de la historia");
            IO.println("4. Borrar registro");
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
                case 1:
                    mostrarHistoriaPaciente();
                    break;
                case 2:
                    añadirRegistro();
                    break;
                case 3:
                    actualizarRegistro();
                    break;
                case 4:
                    borrarRegistro();
                    break;
                case 0:
                    IO.println("Volviendo...");
                    break;
                default:
                    IO.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void mostrarHistoriaPaciente() {
        IO.println("Introduce el ID del paciente: ");
        try {
            Long pacienteId = Long.parseLong(IO.readln());
            List<HistoriaClinicaDTO> lista = service.getHistoriasByPacienteId(pacienteId);

            if (lista.isEmpty()) {
                IO.println("No hay registros en la historia clínica para este paciente.");
            } else {
                IO.println("\n--- HISTORIA CLÍNICA DEL PACIENTE " + pacienteId + " ---");
                for (HistoriaClinicaDTO h : lista) {
                    IO.println("ID Registro: " + h.getHistoriaId() +
                            " | Médico ID: " + h.getMedicoId() +
                            " | Diagnóstico: " + h.getDiagnostico() +
                            " | Fecha Admisión: " + h.getFechaAdmision());
                }
            }
        } catch (NumberFormatException e) {
            IO.println("ID de paciente no válido.");
        }
    }

    private void añadirRegistro() {
        try {
            IO.println("Introduce el ID del paciente: ");
            Long pacienteId = Long.parseLong(IO.readln());

            IO.println("Introduce el ID del médico: ");
            Long medicoId = Long.parseLong(IO.readln());

            IO.println("Introduce el diagnóstico: ");
            String diagnostico = IO.readln();

            IO.println("Introduce la fecha de admisión (AAAA-MM-DD): ");
            LocalDate fecha = LocalDate.parse(IO.readln());

            HistoriaClinicaDTO dto = HistoriaClinicaDTO.builder()
                    .pacienteId(pacienteId)
                    .medicoId(medicoId)
                    .diagnostico(diagnostico)
                    .fechaAdmision(fecha)
                    .build();

            boolean exito = service.saveHistoria(dto);
            if (exito) {
                IO.println("¡Registro añadido con éxito!");
            } else {
                IO.println("No se pudo añadir el registro.");
            }
        } catch (NumberFormatException e) {
            IO.println("Error en los campos numéricos.");
        } catch (DateTimeParseException e) {
            IO.println("Formato de fecha incorrecto. Use AAAA-MM-DD.");
        }
    }

    private void actualizarRegistro() {
        try {
            IO.println("Introduce el ID del registro de historia a actualizar: ");
            Long historiaId = Long.parseLong(IO.readln());

            Optional<HistoriaClinicaDTO> existente = service.getHistoriaById(historiaId);
            if (existente.isEmpty()) {
                IO.println("No se encontró ningún registro con ese ID.");
                return;
            }

            IO.println("Introduce el nuevo ID del paciente: ");
            Long pacienteId = Long.parseLong(IO.readln());

            IO.println("Introduce el nuevo ID del médico: ");
            Long medicoId = Long.parseLong(IO.readln());

            IO.println("Introduce el nuevo diagnóstico: ");
            String diagnostico = IO.readln();

            IO.println("Introduce la nueva fecha de admisión (AAAA-MM-DD): ");
            LocalDate fecha = LocalDate.parse(IO.readln());

            HistoriaClinicaDTO dto = HistoriaClinicaDTO.builder()
                    .historiaId(historiaId)
                    .pacienteId(pacienteId)
                    .medicoId(medicoId)
                    .diagnostico(diagnostico)
                    .fechaAdmision(fecha)
                    .build();

            boolean exito = service.updateHistoria(dto);
            if (exito) {
                IO.println("¡Registro actualizado con éxito!");
            } else {
                IO.println("No se pudo actualizar el registro.");
            }
        } catch (NumberFormatException e) {
            IO.println("Error en los campos numéricos.");
        } catch (DateTimeParseException e) {
            IO.println("Formato de fecha incorrecto. Use AAAA-MM-DD.");
        }
    }

    private void borrarRegistro() {
        IO.println("Introduce el ID del registro de historia a borrar: ");
        try {
            Long historiaId = Long.parseLong(IO.readln());
            boolean exito = service.deleteHistoria(historiaId);
            if (exito) {
                IO.println("¡Registro borrado con éxito!");
            } else {
                IO.println("No se encontró el registro o no se pudo borrar.");
            }
        } catch (NumberFormatException e) {
            IO.println("Por favor, introduzca un ID numérico válido.");
        }
    }
}