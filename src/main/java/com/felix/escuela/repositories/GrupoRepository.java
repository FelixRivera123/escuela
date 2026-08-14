package com.felix.escuela.repositories;

import com.felix.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    boolean existsByMaestroId(Long idMaestro);
    boolean existsByAulaId(Long idAula);
    boolean existsByCursoId(Long idCurso);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo);

    boolean existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(
            Long idCurso,
            Long idMaestro,
            Long idAula,
            String periodo,
            Long id);
}
