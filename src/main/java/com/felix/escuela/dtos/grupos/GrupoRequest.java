package com.felix.escuela.dtos.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record GrupoRequest(

        @NotNull(message = "El curso es requerido")
        Long idCurso,

        @NotNull(message = "El maestro es requerido")
        Long idMaestro,

        @NotNull(message = "El aula es requerida")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "El periodo debe tener el formato YYYY-MM")
        String periodo
) {
}
