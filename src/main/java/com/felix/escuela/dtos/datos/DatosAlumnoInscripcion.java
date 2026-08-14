package com.felix.escuela.dtos.datos;

import java.time.LocalDate;

public record DatosAlumnoInscripcion(
        String nombreCompleto,
        String matricula,
        String email,
        LocalDate fechaIngreso
) {
}
