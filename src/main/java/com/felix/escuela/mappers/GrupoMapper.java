package com.felix.escuela.mappers;

import com.felix.escuela.dtos.grupos.GrupoRequest;
import com.felix.escuela.dtos.grupos.GrupoResponse;
import com.felix.escuela.entities.Aula;
import com.felix.escuela.entities.Curso;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper {

    private  final DatosMapper datosMapper;

    public Grupo requestAEntidad(
            GrupoRequest request,
            Curso curso,
            Maestro maestro,
            Aula aula
    ) {
        if (request == null) return null;

        return Grupo.builder()
                .curso(curso)
                .maestro(maestro)
                .aula(aula)
                .periodo(request.periodo().trim())
                .build();
    }

    public GrupoResponse entidadAResponse(Grupo entidad) {

        if (entidad == null) return null;

        return new GrupoResponse(
                entidad.getId(),
                datosMapper.cursoADatos(entidad.getCurso()),
                datosMapper.maestroADatos(entidad.getMaestro()),
                datosMapper.aulaADatos(entidad.getAula()),
                List.of(),
                entidad.getPeriodo()
        );
    }
}
