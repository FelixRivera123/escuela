package com.felix.escuela.dtos.alumnos;

import com.felix.escuela.dtos.datos.DatosCalificacion;
import com.felix.escuela.entities.Alumno;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record AlumnoResponse(

        Long id,
        String nombre,
        String email,
        String matricula,
        String fechaIngreso,
        List<DatosCalificacion> calificaciones,
        BigDecimal promedio
) {
}
