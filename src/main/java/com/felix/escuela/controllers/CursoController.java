package com.felix.escuela.controllers;

import com.felix.escuela.dtos.cursos.CursoRequest;
import com.felix.escuela.dtos.cursos.CursoResponse;
import com.felix.escuela.services.cursos.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<CursoRequest, CursoResponse, CursoService> {

    public CursoController(CursoService service) {
        super(service);
    }
}
