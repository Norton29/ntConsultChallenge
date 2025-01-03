package com.norton.desafio_NtConsult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.dto.AgendaDTO;
import com.norton.desafio_NtConsult.service.AgendaService;

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

  @PostMapping("/register")
  public ResponseEntity registerAgenda(@RequestBody AgendaDTO agenda) {
    agendaService.registerAgenda(agenda.getDescription());
    return ResponseEntity.created(null).build();
  }

  @GetMapping("/results")
  public ResponseEntity<AgendaDTO> results(@RequestParam Long agendaId) {
    AgendaDTO resultDTO = agendaService.showResults(agendaId);
    return ResponseEntity.ok().body(resultDTO);
  }

}
