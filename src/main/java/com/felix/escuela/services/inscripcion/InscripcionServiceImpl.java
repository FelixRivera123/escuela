package com.felix.escuela.services.inscripcion;

import com.felix.escuela.dtos.inscripciones.InscripcionRequest;
import com.felix.escuela.dtos.inscripciones.InscripcionResponse;
import com.felix.escuela.entities.Alumno;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Inscripcion;
import com.felix.escuela.exceptions.EntidadRelacionadaException;
import com.felix.escuela.mappers.InscripcionMapper;
import com.felix.escuela.repositories.AlumnoRepository;
import com.felix.escuela.repositories.GrupoRepository;
import com.felix.escuela.repositories.InscripcionRepository;
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
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final InscripcionMapper inscripcionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {

        log.info("Listando inscripciones");

        return inscripcionRepository.findAll()
                .stream()
                .map(inscripcionMapper::responseAEntidad)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse obtenerPorId(Long id) {

        log.info("Buscando inscripcion con id: {}", id);

        return inscripcionMapper.responseAEntidad(
                obtenerInscripcion(id)
        );
    }

    @Override
    public InscripcionResponse registrar(
            InscripcionRequest request
    ) {

        log.info("Registrando inscripcion");

        Alumno alumno = obtenerAlumno(
                request.idAlumno()
        );

        Grupo grupo = obtenerGrupo(
                request.idGrupo()
        );

        validarDatosUnicos(request);

        Inscripcion inscripcion =
                inscripcionMapper.requestAEntidad(
                        request,
                        alumno,
                        grupo
                );

        inscripcionRepository.save(inscripcion);

        log.info("Inscripcion registrada con id: {}", inscripcion.getId()
        );

        return inscripcionMapper.responseAEntidad(
                inscripcion
        );
    }

    @Override
    public InscripcionResponse actualizar(
            InscripcionRequest request,
            Long id
    ) {

        log.info("Actualizando inscripcion con id: {}", id);

        Inscripcion inscripcion =
                obtenerInscripcion(id);

        Alumno alumno = obtenerAlumno(
                request.idAlumno()
        );

        Grupo grupo = obtenerGrupo(
                request.idGrupo()
        );

        validarCambiosUnicos(
                request,
                id
        );

        inscripcion.actualizar(
                alumno,
                grupo
        );

        log.info("Inscripcion actualizada con id: {}", id);

        return inscripcionMapper.responseAEntidad(
                inscripcion
        );
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando inscripcion con id: {}", id);

        Inscripcion inscripcion =
                obtenerInscripcion(id);

        if (inscripcion.getCalificacion() != null) {
            throw new EntidadRelacionadaException(
                    "No se puede eliminar la inscripción porque tiene una calificación asignada"
            );
        }

        inscripcionRepository.delete(inscripcion);

        log.info("Inscripcion eliminada con id: {}", id);
    }

    private Inscripcion obtenerInscripcion(Long id) {

        return ServicesUtils.onbtenerEntidadOException(
                inscripcionRepository,
                id,
                Inscripcion.class
        );
    }

    private Alumno obtenerAlumno(Long id) {

        return ServicesUtils.onbtenerEntidadOException(
                alumnoRepository,
                id,
                Alumno.class
        );
    }

    private Grupo obtenerGrupo(Long id) {

        return ServicesUtils.onbtenerEntidadOException(
                grupoRepository,
                id,
                Grupo.class
        );
    }

    private void validarDatosUnicos(
            InscripcionRequest request
    ) {

        if (inscripcionRepository
                .existsByAlumnoIdAndGrupoId(
                        request.idAlumno(),
                        request.idGrupo()
                )) {

            throw new IllegalArgumentException(
                    "El alumno ya está inscrito en este grupo"
            );
        }
    }

    private void validarCambiosUnicos(
            InscripcionRequest request,
            Long idInscripcion
    ) {

        if (inscripcionRepository
                .existsByAlumnoIdAndGrupoIdAndIdNot(
                        request.idAlumno(),
                        request.idGrupo(),
                        idInscripcion
                )) {

            throw new IllegalArgumentException(
                    "El alumno ya está inscrito en este grupo"
            );
        }
    }
}