package com.felix.escuela.dtos.horarios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felix.escuela.enums.DiaSemana;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record HorarioRequest(

        @NotNull(message = "El grupo es requerido")
        Long idGrupo,

        @NotNull(message = "El dia es requerido")
        DiaSemana dia,

        @NotNull(message = "La hora de inicio es requerida")
        @Pattern(
                regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
                message = "La hora de inicio debe tener el formato HH:mm"
        )
        String horaInicio,

        @NotNull(message = "La hora de fin es requerida")
        @Pattern(
                regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
                message = "La hora de fin debe tener el formato HH:mm"
        )
        String horaFin
) {}
