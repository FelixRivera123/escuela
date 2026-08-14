package com.felix.escuela.dtos.calificaciones;

import com.felix.escuela.dtos.datos.DatosAlumnoInscripcion;
import com.felix.escuela.dtos.datos.DatosGrupoHorario;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CalificacionResponse(

        Long id,
        DatosAlumnoInscripcion alumno,
        DatosGrupoHorario grupo,
        BigDecimal calificacion,
        LocalDate fechaRegistro
) {
}
