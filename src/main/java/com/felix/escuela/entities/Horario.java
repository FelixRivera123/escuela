package com.felix.escuela.entities;

import com.felix.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "ID_GRUPO")
    private Grupo grupo;


    @Column(name = "DIA",length = 15, nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;

    @Column(name = "HORA_INICIO",length = 5, nullable = false)
    private String horaInicio;

    @Column(name = "HORA_FIN",length = 5, nullable = false)
    private String horaFin;
}
