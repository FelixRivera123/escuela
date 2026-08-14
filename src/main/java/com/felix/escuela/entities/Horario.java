package com.felix.escuela.entities;

import com.felix.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "HORARIOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_GRUPO", nullable = false)
    private Grupo grupo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIA",length = 15, nullable = false)
    private DiaSemana diaSemana;

    @Column(name = "HORA_INICIO", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "HORA_FIN", nullable = false)
    private LocalTime horaFin;

    private void validarHoras(LocalTime horaInicio, LocalTime horaFin) {
        if(horaInicio == null || horaFin == null)
            throw new IllegalArgumentException("La hora de inicio y la hora de fin son requeridas");

        if (!horaFin.isAfter(horaInicio))
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio");
    }

    public void actualizar(Grupo grupo, DiaSemana diaSemana, LocalTime horaInicio, LocalTime horaFin) {

        validarHoras(horaInicio, horaFin);

        this.grupo = grupo;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
