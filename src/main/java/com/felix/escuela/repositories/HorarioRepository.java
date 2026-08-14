package com.felix.escuela.repositories;

import com.felix.escuela.entities.Horario;
import com.felix.escuela.enums.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    boolean existsByGrupoId(Long idGrupo);

    @Query("""
            SELECT COUNT(h) > 0
            FROM Horario h
            WHERE h.grupo.aula.id = :idAula
            AND h.diaSemana = :dia
            AND h.horaInicio < :horaFin
            AND h.horaFin > :horaInicio
            """)

    boolean existeConflictoAula(
            @Param("idAula") Long idAula,
            @Param("dia") DiaSemana dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin
    );

    @Query("""
            SELECT COUNT(h) > 0
            FROM Horario h
            WHERE h.grupo.maestro.id = :idMaestro
            AND h.diaSemana = :dia
            AND h.horaInicio < :horaFin
            AND h.horaFin > :horaInicio
            """)

    boolean existeConflictoMaestro(
            @Param("idMaestro") Long idMaestro,
            @Param("dia") DiaSemana dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin
    );

    @Query("""
            SELECT COUNT(h) > 0
            FROM Horario h
            WHERE h.grupo.id = :idGrupo
            AND h.diaSemana = :dia
            AND h.horaInicio < :horaFin
            AND h.horaFin > :horaInicio
            """)

    boolean existeConflictoGrupo(
            @Param("idGrupo") Long idGrupo,
            @Param("dia") DiaSemana dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin
    );

    @Query("""
            SELECT COUNT(h) > 0
            FROM Horario h
            WHERE h.grupo.aula.id = :idAula
            AND h.diaSemana = :dia
            AND h.horaInicio < :horaFin
            AND h.horaFin > :horaInicio
            AND h.id <> :idHorario
            """)
    boolean existeConflictoAulaActualizacion(
            @Param("idAula") Long idAula,
            @Param("dia") DiaSemana dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("idHorario") Long idHorario
    );

    @Query("""
            SELECT COUNT(h) > 0
            FROM Horario h
            WHERE h.grupo.maestro.id = :idMaestro
            AND h.diaSemana = :dia
            AND h.horaInicio < :horaFin
            AND h.horaFin > :horaInicio
            AND h.id <> :idHorario
            """)
    boolean existeConflictoMaestroActualizacion(
            @Param("idMaestro") Long idMaestro,
            @Param("dia") DiaSemana dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("idHorario") Long idHorario
    );

    @Query("""
            SELECT COUNT(h) > 0
            FROM Horario h
            WHERE h.grupo.id = :idGrupo
            AND h.diaSemana = :dia
            AND h.horaInicio < :horaFin
            AND h.horaFin > :horaInicio
            AND h.id <> :idHorario
            """)
    boolean existeConflictoGrupoActualizacion(
            @Param("idGrupo") Long idGrupo,
            @Param("dia") DiaSemana dia,
            @Param("horaInicio") String horaInicio,
            @Param("horaFin") String horaFin,
            @Param("idHorario") Long idHorario
    );
}
