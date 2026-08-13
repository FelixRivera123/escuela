package com.felix.escuela.dtos.aulas;

import jakarta.validation.constraints.*;

public record AulaRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 1, max = 100, message = "El nombre debe tener entre 8 y 100 caracteres")
        String nombre,

        @NotNull(message = "La capacidad es requerida")
        @Min(value = 1, message = "La capacidad minima son 1")
        @Max(value = 30, message = "La capacidad maxima es de 30")
        Integer capacidad
) {
}
