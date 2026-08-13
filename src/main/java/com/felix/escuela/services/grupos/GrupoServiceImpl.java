package com.felix.escuela.services.grupos;

import com.felix.escuela.dtos.grupos.GrupoRequest;
import com.felix.escuela.dtos.grupos.GrupoResponse;
import com.felix.escuela.entities.Grupo;
import com.felix.escuela.entities.Maestro;
import com.felix.escuela.mappers.GrupoMapper;
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
public class GrupoServiceImpl implements GrupoService {

    private GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<GrupoResponse> listar() {
        log.info("Listando todos los grupos");
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse)
                .toList();
    }
//Simpre para obtener por id se requiere crear el metodo anteriormente
    @Override
    public GrupoResponse obtenerPorId(Long id) {
        return grupoMapper.entidadAResponse(obtenerGrupo(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando grupo");
        return null;
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    private Grupo obtenerGrupo(Long id){
        return ServicesUtils.onbtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private void validarDatosUnicos(GrupoMapper request){
        log.info("Validando curso unico");

        ;
    }
}
