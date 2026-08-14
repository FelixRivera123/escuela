package com.felix.escuela.controllers;

import com.felix.escuela.dtos.horarios.HorarioRequest;
import com.felix.escuela.dtos.horarios.HorarioResponse;
import com.felix.escuela.services.horarios.HorarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController extends CommonController <HorarioRequest, HorarioResponse, HorarioService> {
    public HorarioController(HorarioService service) {
        super(service);
    }
}
