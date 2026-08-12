package com.felix.escuela.dtos.maestros;

import com.felix.escuela.dtos.datos.DatosCurso;

import java.util.List;

public record MaestroResponse(

        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos
) {
}
