package com.felix.escuela.controllers;

import com.felix.escuela.dtos.aulas.AulaRequest;
import com.felix.escuela.dtos.aulas.AulaResponse;
import com.felix.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService> {

    public AulaController(AulaService service) {super(service);}
}
