package com.felix.escuela.controllers;

import com.felix.escuela.dtos.calificaciones.CalificacionRequest;
import com.felix.escuela.dtos.calificaciones.CalificacionResponse;
import com.felix.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionRequest, CalificacionResponse, CalificacionService>{
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}
