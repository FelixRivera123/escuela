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

    @Column(name = "HORA_INICIO", length = 5, nullable = false)
    private String horaInicio;

    @Column(name = "HORA_FIN", length = 5, nullable = false)
    private String horaFin;

    private void validarHoras(String horaInicio, String horaFin) {

        if (horaInicio == null || horaFin == null) {
            throw new IllegalArgumentException(
                    "La hora de inicio y la hora de fin son requeridas"
            );
        }

        LocalTime inicio = LocalTime.parse(horaInicio);
        LocalTime fin = LocalTime.parse(horaFin);

        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException(
                    "La hora de fin debe ser posterior a la hora de inicio"
            );
        }
    }

    public void actualizar(
            Grupo grupo,
            DiaSemana diaSemana,
            String horaInicio,
            String horaFin
    ) {
        validarHoras(horaInicio, horaFin);

        this.grupo = grupo;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public static Horario crear(
            Grupo grupo,
            DiaSemana diaSemana,
            String horaInicio,
            String horaFin
    ){
        Horario horario = new Horario();

        horario.validarHoras(horaInicio, horaFin);

        horario.grupo = grupo;
        horario.diaSemana = diaSemana;
        horario.horaInicio = horaInicio;
        horario.horaFin = horaFin;

        return horario;
    }
}
