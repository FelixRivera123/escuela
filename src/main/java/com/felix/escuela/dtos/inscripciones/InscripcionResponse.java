package com.felix.escuela.dtos.inscripciones;

import com.felix.escuela.dtos.datos.DatosAlumnoInscripcion;
import com.felix.escuela.dtos.datos.DatosCalificacion;
import com.felix.escuela.dtos.datos.DatosGrupoHorario;

import java.time.LocalDate;

public record InscripcionResponse(
        Long id,
        DatosAlumnoInscripcion alumno,
        DatosGrupoHorario grupo,
        DatosCalificacion calificacion,
        LocalDate fechaIngreso
) {
}
