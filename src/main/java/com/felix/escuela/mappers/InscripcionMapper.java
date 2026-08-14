package com.felix.escuela.mappers;

import com.felix.escuela.dtos.inscripciones.InscripcionRequest;
import com.felix.escuela.dtos.inscripciones.InscripcionResponse;
import com.felix.escuela.entities.Alumno;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Inscripcion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InscripcionMapper {

    private final DatosMapper datosMapper;

    public Inscripcion requestAEntidad(
            InscripcionRequest request,
            Alumno alumno,
            Grupo grupo
    ){
        if (request == null) return null;

        return Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .build();
    }

    public InscripcionResponse responseAEntidad(
            Inscripcion entidad
    ){
        if (entidad == null) return null;

        return new  InscripcionResponse(
                entidad.getId(),
                datosMapper.alumnoADatosInscripcion(
                        entidad.getAlumno()),
                datosMapper.grupoADatosHorario(
                        entidad.getGrupo()),
                datosMapper.calificacionADatos(entidad.getCalificacion()),
                entidad.getFechaInscripcion()
        );
    }

}
