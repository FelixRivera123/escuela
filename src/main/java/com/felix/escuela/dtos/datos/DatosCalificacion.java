package com.felix.escuela.dtos.datos;

import java.math.BigDecimal;

public record DatosCalificacion(

        String curso,
        String periodo,
        BigDecimal calificacion
) {
}
