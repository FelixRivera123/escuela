package com.felix.escuela.dtos.grupos;

import com.felix.escuela.dtos.datos.DatosAula;
import com.felix.escuela.dtos.datos.DatosCurso;
import com.felix.escuela.dtos.datos.DatosMaestro;


import java.util.List;

public record GrupoResponse(

    Long id,
    DatosCurso curso,
    DatosMaestro maestro,
    DatosAula aula,
    List<String> horarios,
    String periodo
) {
}
