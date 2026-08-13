package com.felix.escuela.mappers;

import com.felix.escuela.dtos.datos.DatosAula;
import com.felix.escuela.dtos.datos.DatosCurso;
import com.felix.escuela.dtos.datos.DatosMaestro;
import com.felix.escuela.entities.Aula;
import com.felix.escuela.entities.Curso;
import com.felix.escuela.entities.Maestro;
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
                maestro.getNombre(),
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
}
