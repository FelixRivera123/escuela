package com.felix.escuela.services.calificaciones;

import com.felix.escuela.dtos.calificaciones.CalificacionRequest;
import com.felix.escuela.dtos.calificaciones.CalificacionResponse;
import com.felix.escuela.entities.Calificacion;
import com.felix.escuela.entities.Inscripcion;
import com.felix.escuela.mappers.CalificacionMapper;
import com.felix.escuela.repositories.CalificacionRepository;
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
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;
    private final CalificacionMapper calificacionMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listar() {

        log.info("Listando calificaciones");

        return calificacionRepository.findAll()
                .stream()
                .map(calificacionMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionResponse obtenerPorId(Long id) {

        log.info("Buscando calificación con id: {}", id);

        return calificacionMapper.entidadAResponse(
                obtenerCalificacion(id)
        );
    }

    @Override
    public CalificacionResponse registrar(
            CalificacionRequest request
    ) {
        log.info("Registrando calificación");

        Inscripcion inscripcion = obtenerInscripcion(
                        request.idInscripcion()
                );

        validarInscripcionSinCalificacion(
                request.idInscripcion()
        );

        Calificacion calificacion = calificacionMapper.requestAEntidad(
                        request,
                        inscripcion
                );

        calificacionRepository.save(calificacion);

        log.info("Calificación registrada con id: {}", calificacion.getId()
        );

        return calificacionMapper.entidadAResponse(
                calificacion
        );
    }

    @Override
    public CalificacionResponse actualizar(
            CalificacionRequest request,
            Long id
    ) {
        log.info("Actualizando calificación con id: {}", id);

        Calificacion calificacion = obtenerCalificacion(id);

        Inscripcion inscripcion = obtenerInscripcion(
                        request.idInscripcion()
                );

        validarInscripcionActualizacion(
                request.idInscripcion(),
                id
        );

        if (!calificacion.getInscripcion()
                .getId()
                .equals(inscripcion.getId())) {

            throw new IllegalArgumentException(
                    "No se puede cambiar la inscripción de una calificación"
            );
        }

        calificacion.actualizar(request.calificacion());

        log.info("Calificación actualizada con id: {}", id);

        return calificacionMapper.entidadAResponse(
                calificacion
        );
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando calificación con id: {}", id);

        Calificacion calificacion = obtenerCalificacion(id);

        calificacionRepository.delete(calificacion);

        log.info("Calificación eliminada con id: {}", id);
    }

    private Calificacion obtenerCalificacion(Long id) {

        return ServicesUtils.onbtenerEntidadOException(
                calificacionRepository,
                id,
                Calificacion.class
        );
    }

    private Inscripcion obtenerInscripcion(Long id) {

        return ServicesUtils.onbtenerEntidadOException(
                inscripcionRepository,
                id,
                Inscripcion.class
        );
    }

    private void validarInscripcionSinCalificacion(
            Long idInscripcion
    ) {

        if (calificacionRepository
                .existsByInscripcionId(idInscripcion)) {

            throw new IllegalArgumentException(
                    "La inscripción ya tiene una calificación registrada"
            );
        }
    }

    private void validarInscripcionActualizacion(
            Long idInscripcion,
            Long idCalificacion
    ) {

        if (calificacionRepository
                .existsByInscripcionIdAndIdNot(
                        idInscripcion,
                        idCalificacion
                )) {

            throw new IllegalArgumentException(
                    "La inscripción ya tiene una calificación registrada"
            );
        }
    }
}