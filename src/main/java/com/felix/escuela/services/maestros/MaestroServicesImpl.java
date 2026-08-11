package com.felix.escuela.services.maestros;

import com.felix.escuela.dtos.maestros.MaestroRequest;
import com.felix.escuela.dtos.maestros.MaestroResponse;
import com.felix.escuela.entities.Maestro;
import com.felix.escuela.mappers.MaestroMapper;
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
public class MaestroServicesImpl implements MaestroService {

    private final MaestroRepository maestroRepository;

    private final MaestroMapper maestroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        log.info("Listando todos los maestros");

        return maestroRepository.findAll().stream()
                .map(maestroMapper::entidadAResponse)
                .toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        return maestroMapper.entidadAResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {

        log.info("Registrando nuevo maestro...");

        Maestro maestro = maestroMapper.requestAEntidad(request);

        log.info("Nuevo maestro {} registrado", maestro.getNombre());

        maestroRepository.save(maestro);

        return maestroMapper.entidadAResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    private Maestro obtenerMaestro(Long id) {
        return ServicesUtils.ontenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    private void validarDatosUnicos(MaestroRequest request){

        log.info ("Validando email único");

        if(maestroRepository.existsByEmailIgnoreCase(request.email())){
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email: " + request.email());
        }

        log.info ("Validando teléfono único");

        if(maestroRepository.existsByTelefono(request.telefono()))
            throw new IllegalArgumentException("Ya existe un maestro registrando con el teléfono: " + request.telefono());


    }
}