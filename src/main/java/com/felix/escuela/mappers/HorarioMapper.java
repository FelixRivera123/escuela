package com.felix.escuela.mappers;

import com.felix.escuela.dtos.horarios.HorarioRequest;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Horario;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMapper {

    private final DatosMapper datosMapper;

    public Horario requestAEntidad(
            HorarioRequest request,
            Grupo grupo
    ){
        if (request == null) return null;

        return Horario.builder()
                .grupo(grupo)
                .diaSemana(request.dia())
                .horaInicio(request.horaInicio())
                .horaFin(request.horaFin())
                .build();
    }
}
