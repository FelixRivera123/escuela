package com.felix.escuela.services.alumnos;

import com.felix.escuela.dtos.alumnos.AlumnoRequest;
import com.felix.escuela.dtos.alumnos.AlumnoResponse;
import com.felix.escuela.entities.Alumno;
import com.felix.escuela.exceptions.EntidadRelacionadaException;
import com.felix.escuela.mappers.AlumnoMapper;
import com.felix.escuela.repositories.AlumnoRepository;
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
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

    private final InscripcionRepository inscripcionRepository;

    private final AlumnoMapper alumnoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Listando todos los alumnos");

        return alumnoRepository.findAll().stream()
                .map(alumnoMapper::entidadAResponse).toList();
    }

    @Override
    public AlumnoResponse obtenerPorId(Long id) {
        log.info("Obteniendo todos los alumnos por id: {}", id);
        return alumnoMapper.entidadAResponse(obtenerAlumno(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registrando nuevo alumno...");

        Alumno alumno = alumnoMapper.requestAEntidad(
                request,
                generarEmail(request),
                generarMatricual(request)

        );

        alumnoRepository.save(alumno);

        log.info("Nuevo alumno {} registrado correctamente", alumno.getNombre());

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {
        Alumno alumno = obtenerAlumno(id);

        log.info("Actualizando alumno con id: {}", id);

        if (alumno.cambioEnDatos(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim())){

            alumno.actualizar(
                    request.nombre(),
                    request.apellidoPaterno(),
                    request.apellidoMaterno(),
                    generarEmail(request),
                    generarMatricual(request)
            );

            log.info("Datos academicos regenerados para el alumno con id: {}", id);
        }

        return alumnoMapper.entidadAResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {

        Alumno alumno = obtenerAlumno(id);

        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el alumno ya que tiene grupos asignados");

        alumnoRepository.delete(alumno);

        log.info("Alumno con id: {} eliminado", id);
    }

    private Alumno obtenerAlumno(Long id) {
        return ServicesUtils.onbtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }

    private String generarMatricual(AlumnoRequest request){
        log.info("Generando matricula del alumno...");

        String matricula = alumnoRepository.generarMatricula(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());

        log.info("Matricula {} generado correctamente", matricula);
        return matricula;
    }

    private String generarEmail(AlumnoRequest request){
        log.info("Generando email del alumno...");

        String email = alumnoRepository.generarEmail(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim());

        log.info("Email {} generado correctamente", email);
        return email;
    }
}
