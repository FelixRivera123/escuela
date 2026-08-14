package com.felix.escuela.mappers;

import com.felix.escuela.dtos.calificaciones.CalificacionRequest;
import com.felix.escuela.dtos.calificaciones.CalificacionResponse;
import com.felix.escuela.entities.Calificacion;
import com.felix.escuela.entities.Inscripcion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CalificacionMapper {

    private final DatosMapper datosMapper;

    public Calificacion requestAEntidad(
            CalificacionRequest request,
            Inscripcion inscripcion
    ) {
        if (request == null) return null;

        return Calificacion.crear(
                inscripcion,
                request.calificacion()
        );
    }

    public CalificacionResponse entidadAResponse(
            Calificacion entidad
    ) {
        if (entidad == null) return null;

        return new CalificacionResponse(
                entidad.getId(),

                datosMapper.alumnoADatosInscripcion(
                        entidad.getInscripcion().getAlumno()
                ),

                datosMapper.grupoADatosHorario(
                        entidad.getInscripcion().getGrupo()
                ),
                entidad.getCalificacion(),
                entidad.getFechaRegistro()
        );
    }
}