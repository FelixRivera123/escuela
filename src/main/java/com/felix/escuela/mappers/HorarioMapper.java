package com.felix.escuela.mappers;

import com.felix.escuela.dtos.horarios.HorarioRequest;
import com.felix.escuela.dtos.horarios.HorarioResponse;
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

        return Horario.crear(
                grupo,
                request.dia(),
                request.horaInicio(),
                request.horaFin()
        );
    };

    public HorarioResponse entidadAResponse(Horario entidad){
        if (entidad == null) return null;

        String horario = entidad.getDiaSemana() + " "
                + entidad.getHoraInicio() + " - "
                + entidad.getHoraFin();

        return new HorarioResponse(
                entidad.getId(),
                datosMapper.grupoADatosHorario(
                        entidad.getGrupo()), horario
        );
    }
}
