package com.felix.escuela.controllers;

import com.felix.escuela.dtos.inscripciones.InscripcionRequest;
import com.felix.escuela.dtos.inscripciones.InscripcionResponse;
import com.felix.escuela.services.inscripcion.InscripcionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends CommonController<InscripcionRequest, InscripcionResponse, InscripcionService>{
    public InscripcionController(InscripcionService service) {
        super(service);
    }
}
