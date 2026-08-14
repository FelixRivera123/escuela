package com.felix.escuela.services.horarios;

import com.felix.escuela.dtos.horarios.HorarioRequest;
import com.felix.escuela.dtos.horarios.HorarioResponse;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Horario;
import com.felix.escuela.mappers.HorarioMapper;
import com.felix.escuela.repositories.GrupoRepository;
import com.felix.escuela.repositories.HorarioRepository;
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
public class HorarioServiceImpl implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {

        log.info("Iniciando lista de horarios");

        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        log.info("Buscando horario con id: {}", id);
        return horarioMapper.entidadAResponse(
                obtenerHorario(id)
        );
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Iniciando registro de horario");

        Grupo grupo = obtenerGrupo(request.idGrupo());

        validarConflictos(request, grupo);

        Horario horario = horarioMapper.requestAEntidad(request, grupo);

        horarioRepository.save(horario);

        log.info("Horario registrado con id: {}", horario.getId());

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {

        log.info("Actualizando horario con el id: {}", id );

        Horario horario = obtenerHorario(id);
        Grupo grupo = obtenerGrupo(request.idGrupo());

        validarConflictosActualizacion(
                request,
                grupo,
                id
        );

        horario.actualizar(
                grupo,
                request.dia(),
                request.horaInicio(),
                request.horaFin()
        );

        log.info("Horario actualizado con id: {}", id);

        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando horario con id: {}", id);

        Horario horario = obtenerHorario(id);

        horarioRepository.delete(horario);

        log.info("Horario eliminado con id: {}", id);
    }

    private Horario obtenerHorario(Long id){
        return ServicesUtils.onbtenerEntidadOException(
                horarioRepository,
                id,
                Horario.class
        );
    }

    private Grupo obtenerGrupo(Long id){
        return ServicesUtils.onbtenerEntidadOException(
                grupoRepository,
                id,
                Grupo.class
        );
    }

    private void validarConflictos(HorarioRequest request, Grupo grupo){
        if (horarioRepository.existeConflictoAula(
                grupo.getAula().getId(),
                request.dia(),
                request.horaInicio(),
                request.horaFin()))
            throw new IllegalArgumentException("El aula ya tiene un horario asignado en ese periodo");

        if (horarioRepository.existeConflictoMaestro(
                grupo.getMaestro().getId(),
                request.dia(),
                request.horaInicio(),
                request.horaFin()))
            throw new IllegalArgumentException("El maestro ya tiene un horario asignado en ese periodo");

        if (horarioRepository.existeConflictoGrupo(
                grupo.getId(),
                request.dia(),
                request.horaInicio(),
                request.horaFin()))
            throw new IllegalArgumentException("El grupo ya tiene un horario asignado en ese periodo");
    }

    private void validarConflictosActualizacion(
            HorarioRequest request,
            Grupo grupo,
            Long idHorario
    ) {

        if (horarioRepository.existeConflictoAulaActualizacion(
                grupo.getAula().getId(),
                request.dia(),
                request.horaInicio(),
                request.horaFin(),
                idHorario)) {

            throw new IllegalArgumentException(
                    "El aula ya tiene un horario asignado en ese periodo"
            );
        }

        if (horarioRepository.existeConflictoMaestroActualizacion(
                grupo.getMaestro().getId(),
                request.dia(),
                request.horaInicio(),
                request.horaFin(),
                idHorario)) {

            throw new IllegalArgumentException(
                    "El maestro ya tiene un horario asignado en ese periodo"
            );
        }

        if (horarioRepository.existeConflictoGrupoActualizacion(
                grupo.getId(),
                request.dia(),
                request.horaInicio(),
                request.horaFin(),
                idHorario)) {

            throw new IllegalArgumentException(
                    "El grupo ya tiene un horario asignado en ese periodo"
            );
        }
    }
}
