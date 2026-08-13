package com.felix.escuela.services.aulas;

import com.felix.escuela.dtos.aulas.AulaRequest;
import com.felix.escuela.dtos.aulas.AulaResponse;
import com.felix.escuela.entities.Aula;
import com.felix.escuela.exceptions.EntidadRelacionadaException;
import com.felix.escuela.mappers.AulaMapper;
import com.felix.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;
    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        log.info("Iniciando lista de aulas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse).toList();
    }

    @Override
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadAResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Iniciando registro de aula");

        validarDatosUnicos(request);

        Aula aula = aulaMapper.requestAEntidad(request);

        log.info("Nueva aula {} registrada", aula.getNombre());

        aulaRepository.save(aula);

        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {

        Aula aula = obtenerAula(id);

        log.info("Iniciando actualizacion de aula");

        validarCambiosUnicos(request, id);

        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );

        log.info("Aula {} actualizada correctamente", aula.getNombre());
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {

        Aula aula = obtenerAula(id);

        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException("El aula no puede eliminarse por tener relaciones con grupos");

        log.info("Eliminando aula con id: {}", id);

        aulaRepository.delete(aula);

        log.info("Aula {} eliminada correctamente", id);
    }

    private Aula obtenerAula(Long id){
        return ServicesUtils.onbtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private void validarDatosUnicos(AulaRequest request){

        log.info ("Validando nombre único");

        if(aulaRepository.existsByNombreIgnoreCase(request.nombre())){
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
        }
    }

    private void validarCambiosUnicos(AulaRequest request, Long id) {
        log.info ("Validando nombre único");

        if(aulaRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre(),id)){
            throw new IllegalArgumentException("Ya existe un aula registrada con el nombre: " + request.nombre());
        }

    }
}
