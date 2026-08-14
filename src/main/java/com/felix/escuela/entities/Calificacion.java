package com.felix.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CALIFICACIONES")
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_INSCRIPCION", nullable = false, unique = true)
    private Inscripcion inscripcion;

    @Column(name = "CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    @Builder.Default
    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    private void validarCalificacion(BigDecimal calificacion) {
        if (calificacion == null)
            throw new IllegalArgumentException("La calificacion es requerida");

        if(calificacion.compareTo(BigDecimal.ZERO) < 0 || calificacion.compareTo(BigDecimal.TEN) > 0)
            throw new IllegalArgumentException("La calificacion es entre 0 y 10");
    }

    public static Calificacion crear(
            Inscripcion inscripcion,
            BigDecimal calificacion
    ){
        if (inscripcion == null)
            throw new IllegalArgumentException("La inscripcion es requerida");

        Calificacion nueva = new Calificacion();

        nueva.validarCalificacion(calificacion);
        nueva.inscripcion = inscripcion;
        nueva.calificacion = calificacion;
        nueva.fechaRegistro = LocalDate.now();

        return nueva;
    }

    public void actualizar(BigDecimal calificacion) {
        validarCalificacion(calificacion);
        this.calificacion = calificacion;
    }
}
