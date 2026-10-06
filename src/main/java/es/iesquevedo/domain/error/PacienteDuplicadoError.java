package es.iesquevedo.domain.error;

import es.iesquevedo.common.Constantes;

public class PacienteDuplicadoError extends DatabaseError {
    public PacienteDuplicadoError() {
        super(Constantes.PACIENTE_DUPLLICADO_ERROR);
    }
}
