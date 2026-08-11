package com.felix.escuela.dtos;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) { }
