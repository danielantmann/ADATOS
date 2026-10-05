package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.MedicoDTO;
import es.iesquevedo.domain.services.MedicoService;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

public class MedicoUI {
    private final MedicoService medicoService;

    @Inject
    public MedicoUI(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    public void mostrarMenuMedicos() {
        int opcion = -1;
        do {
            IO.println("\n--- GESTIÓN DE MÉDICOS ---");
            IO.println("1. Mostrar todos los médicos");
            IO.println("2. Buscar médico por ID");
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
                    listarMedicos();
                    break;
                case 2:
                    buscarMedicoPorId();
                    break;
                case 0:
                    IO.println("Volviendo...");
                    break;
                default:
                    IO.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void listarMedicos() {
        List<MedicoDTO> lista = medicoService.getAllMedicos();
        if (lista.isEmpty()) {
            IO.println("No hay médicos registrados.");
        } else {
            IO.println("\n--- LISTA DE MÉDICOS ---");
            for (MedicoDTO m : lista) {
                IO.println("ID: " + m.getMedicoId() +
                        " | Nombre: " + m.getNombre() +
                        " | Especialidad: " + m.getEspecialidad() +
                        " | Teléfono: " + m.getTelefono());
            }
        }
    }

    private void buscarMedicoPorId() {
        IO.println("Introduce el ID del médico: ");
        try {
            Long id = Long.parseLong(IO.readln());
            Optional<MedicoDTO> medico = medicoService.getMedicoById(id);

            if (medico.isPresent()) {
                MedicoDTO m = medico.get();
                IO.println("\n--- MÉDICO ENCONTRADO ---");
                IO.println("ID: " + m.getMedicoId());
                IO.println("Nombre: " + m.getNombre());
                IO.println("Especialidad: " + m.getEspecialidad());
                IO.println("Teléfono: " + m.getTelefono());
            } else {
                IO.println("No se ha encontrado ningún médico con ese ID.");
            }
        } catch (NumberFormatException e) {
            IO.println("Por favor, introduzca un ID numérico válido.");
        }
    }
}