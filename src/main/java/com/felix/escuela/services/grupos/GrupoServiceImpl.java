package com.felix.escuela.services.grupos;

import com.felix.escuela.dtos.grupos.GrupoRequest;
import com.felix.escuela.dtos.grupos.GrupoResponse;
import com.felix.escuela.entities.Aula;
import com.felix.escuela.entities.Curso;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Maestro;
import com.felix.escuela.mappers.GrupoMapper;
import com.felix.escuela.repositories.AulaRepository;
import com.felix.escuela.repositories.CursoRepository;
import com.felix.escuela.repositories.GrupoRepository;
import com.felix.escuela.repositories.MaestroRepository;
import com.felix.escuela.utils.ServicesUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class GrupoServiceImpl implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final GrupoMapper grupoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {
        log.info("Listando todos los grupos");
        return grupoRepository.findAll()
                .stream()
                .map(grupoMapper::entidadAResponse)
                .toList();
    }
//Simpre para obtener por id se requiere crear el metodo anteriormente
    @Override
    public GrupoResponse obtenerPorId(Long id) {
        log.info("Buscando grupo con id: {}", id);
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando grupo");

        Curso curso = obtenerCurso(request.idCurso());
        Maestro maestro = obtenerMaestro(request.idMaestro());
        Aula aula = obtenerAula(request.idAula());

        validarDatosUnicos(request);

        Grupo grupo = grupoMapper.requestAEntidad(
                request,
                curso,
                maestro,
                aula
        );

        grupoRepository.save(grupo);

        log.info("Grupo registrado con id: {}", grupo.getId());

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Grupo grupo = obtenerGrupo(id);

        log.info("Actualizando grupo con id: {}",id);

        Curso curso = obtenerCurso(request.idCurso());
        Maestro maestro = obtenerMaestro(request.idMaestro());
        Aula aula = obtenerAula(request.idAula());

        validarCambiosUnicos(request, id);

        grupo.actualizar(
                curso,
                maestro,
                aula,
                request.periodo()
        );

        log.info("Grupo actualizado con id: {}", grupo.getId());

        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);

    }

    private Grupo obtenerGrupo(Long id){
        return ServicesUtils.onbtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private void validarDatosUnicos(GrupoRequest request) {
        log.info("Validando datos unicos");

        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo()))
             throw new IllegalArgumentException("Ya existe un grupo con el mismo curso, maestro, aula, periodo");
    }

    private void validarCambiosUnicos(GrupoRequest request, Long id) {
        log.info("Validando cambios unicos");

        if (grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
                request.idCurso(),
                request.idMaestro(),
                request.idAula(),
                request.periodo(),
                id))
            throw new IllegalArgumentException("Ya existe un grupo con el mimsmo curso, maestro, aula, periodo");
    }

    private Curso obtenerCurso(Long id) {
        return ServicesUtils.onbtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private Maestro obtenerMaestro(Long id) {
        return ServicesUtils.onbtenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    private Aula obtenerAula(Long id) {
        return ServicesUtils.onbtenerEntidadOException(aulaRepository, id, Aula.class);
    }

}
