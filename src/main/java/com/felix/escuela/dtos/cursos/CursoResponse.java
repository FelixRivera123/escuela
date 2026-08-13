package com.felix.escuela.dtos.cursos;

public record CursoResponse(

        Long id,
        String nombre,
        String descripcion,
        Integer creditos
) {
}
