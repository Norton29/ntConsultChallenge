package com.norton.desafio_NtConsult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.dto.AgendaDTO;
import com.norton.desafio_NtConsult.service.AgendaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    AgendaService agendaService;

    @GetMapping
    public ResponseEntity<List<AgendaDTO>> agendas() {
        List<AgendaDTO> agendas = agendaService.getAgendas();
        return ResponseEntity.ok(agendas);
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity registerAgenda(@RequestBody String agenda) {
        agendaService.registerAgenda(agenda);
        return ResponseEntity.created(null).build();
    }
    
    
}
