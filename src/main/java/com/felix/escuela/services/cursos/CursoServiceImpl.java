package com.felix.escuela.services.cursos;

import com.felix.escuela.dtos.aulas.AulaRequest;
import com.felix.escuela.dtos.cursos.CursoRequest;
import com.felix.escuela.dtos.cursos.CursoResponse;
import com.felix.escuela.entities.Curso;
import com.felix.escuela.exceptions.EntidadRelacionadaException;
import com.felix.escuela.mappers.CursoMapper;
import com.felix.escuela.repositories.CursoRepository;
import com.felix.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl implements CursoService {
    //Siempre instanciar el repository
    private final CursoRepository cursoRepository;
    //Instanciar el GrupoRepository ya que curso pertenece a grupo por lo tanto se ocupara para eliminar en caso de que el curso no tenga grupo asignado
    private final GrupoRepository grupoRepository;
    //Siempre instanciar el mapper
    private final CursoMapper cursoMapper;



    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {

        log.info("Iniciando lista de cursos");

        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse).toList();
    }
   //Antes de obtener por id crear un metodo que haga la busqueda
    @Override
    public CursoResponse obtenerPorId(Long id) {
        return cursoMapper.entidadAResponse(obtenerCurso(id));
    }
/// Crear el metodo que valide datos unicos antes de hacer cualquier registro
    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Iniciando registro de curso");

        validarDatosUnicos(request);

        Curso curso = cursoMapper.requestAEntidad(request);

        log.info("Nuevo curso {} registrado", curso.getNombre());

        cursoRepository.save(curso);

        return cursoMapper.entidadAResponse(curso);
    }
//Antes de actualizar validar cambios unicos
    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {

        Curso curso = obtenerCurso(id);

        log.info("Iniciando actualizacion de curso");

        validarCambiosUnicos(request, id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );

        log.info("Curso {} actualizado correctamente", curso.getNombre());
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public void eliminar(Long id) {

        Curso curso = obtenerCurso(id);

        log.info("Eliminando curso con id: {}", id);

        if(grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("El curso no puede eliminarse por tener relaciones con grupos");

        cursoRepository.delete(curso);

        log.info("Curso {} eliminado correctamente", id);
    }

    private Curso obtenerCurso(Long id) {
        return ServicesUtils.onbtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private void validarDatosUnicos(CursoRequest request){
        log.info("Validando nombre unico");

        if (cursoRepository.existsByNombreIgnoreCase(request.nombre())){
            throw new IllegalArgumentException("Ya existe un curso con el nombre: " + request.nombre());
        }
    }

    private void validarCambiosUnicos(CursoRequest request, Long id) {
        log.info ("Validando nombre único");

        if(cursoRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(),id)){
            throw new IllegalArgumentException("Ya existe un curso registrado con el nombre: " + request.nombre());
        }

    }
}
