package com.felix.escuela.mappers;

import com.felix.escuela.dtos.datos.DatosCurso;
import com.felix.escuela.dtos.grupos.GrupoRequest;
import com.felix.escuela.dtos.grupos.GrupoResponse;
import com.felix.escuela.entities.Grupo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo> {

    private final CursoMapper cursoMapper;
    private final MaestroMapper maestroMapper;
    private final AulaMapper aulaMapper;

    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        if (request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo())
                .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {
        return null;
    }

    private List<DatosCurso> entidadGrupoADatosCurso(Grupo entidad){
        if (entidad == null) return List.of();

        return null;
    }
}
