package com.felix.escuela.dtos.horarios;

import com.felix.escuela.dtos.datos.DatosCurso;
import com.felix.escuela.dtos.datos.DatosGrupoHorario;

public record HorarioResponse(

        Long id,
        DatosGrupoHorario grupo,
        String horario
) {
}
