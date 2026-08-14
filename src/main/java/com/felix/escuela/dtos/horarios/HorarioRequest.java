package com.felix.escuela.dtos.horarios;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.felix.escuela.enums.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record HorarioRequest(

        @NotNull(message = "El grupo es requerido")
        Long idGrupo,

        @NotNull(message = "El dia es requerido")
        DiaSemana dia,

        @NotNull(message = "La hora de inicio es requerida")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaInicio,

        @NotNull(message = "La hora de fin es requerida")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaFin
) {
}
