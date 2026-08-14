package com.felix.escuela.mappers;

import com.felix.escuela.dtos.datos.*;
import com.felix.escuela.entities.*;
import org.springframework.stereotype.Component;

@Component
public class DatosMapper {

    public DatosCurso cursoADatos(Curso curso) {
        if (curso == null) return null;

        return new DatosCurso(
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getCreditos()
        );
    }

    public DatosMaestro maestroADatos(Maestro maestro) {
        if (maestro == null) return null;

        return new DatosMaestro(
                String.join(" ",
                        maestro.getNombre(),
                        maestro.getApellidoPaterno(),
                        maestro.getApellidoMaterno()),
                maestro.getEmail(),
                maestro.getTelefono()
        );
    }

    public DatosAula aulaADatos(Aula aula) {
        if (aula == null) return null;

        return new DatosAula(
                aula.getNombre(),
                aula.getCapacidad()
        );
    }

    public DatosGrupoHorario grupoADatosHorario(Grupo grupo) {
        if (grupo == null) return null;

        return new DatosGrupoHorario(
                grupo.getCurso().getNombre(),
                grupo.getMaestro().nombreCompleto(),
                grupo.getAula().getNombre(),
                grupo.getPeriodo()
        );
    }


     public DatosHorario horarioADatos(Horario horario) {
        if (horario == null) return null;

        return new DatosHorario(
                horario.getDiaSemana().name(),
                horario.getHoraInicio(),
                horario.getHoraFin()
        );
    }

    public DatosAlumnoInscripcion alumnoADatosInscripcion(Alumno alumno) {
        if (alumno == null) return null;

        return new DatosAlumnoInscripcion(
                alumno.nombreCompleto(),
                alumno.getMatricula(),
                alumno.getEmail(),
                alumno.getFechaIngreso()
        );
    }

    public DatosCalificacion calificacionADatos(
            Calificacion calificacion
    ) {

        if (calificacion == null) return null;

        return new DatosCalificacion(
                calificacion.getInscripcion()
                        .getGrupo()
                        .getCurso()
                        .getNombre(),

                calificacion.getInscripcion()
                        .getGrupo()
                        .getPeriodo(),

                calificacion.getCalificacion()
        );
    }
}
