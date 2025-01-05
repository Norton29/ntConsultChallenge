package com.norton.desafio_NtConsult.infra.inbound.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;
import com.norton.desafio_NtConsult.application.ports.in.IAgendaServicePort;
import com.norton.desafio_NtConsult.infra.inbound.dto.AgendaDTO;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/agenda")
@AllArgsConstructor
public class AgendaController {

  private final  IAgendaServicePort agendaService;

  private final Mappers mappers;

  @GetMapping
  public ResponseEntity<List<AgendaDTO>> agendas() {
    List<Agenda> list = agendaService.find();
   List<AgendaDTO> agendasDTOs = list.stream().map(agenda -> mappers.agendaToAgendaDTO(agenda))
        .collect(Collectors.toList());
    return ResponseEntity.ok(agendasDTOs);
  }

  @SuppressWarnings("rawtypes")
  @PostMapping("/register")
  public ResponseEntity registerAgenda(@RequestBody AgendaDTO agenda) throws Exception {
    agendaService.registerAgenda(agenda.getDescription());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/results")
  public ResponseEntity<AgendaDTO> results(@RequestParam Long agendaId) {
    Agenda agenda = agendaService.showResults(agendaId);
    AgendaDTO agendaDTO = mappers.agendaToAgendaDTO(agenda);
    return ResponseEntity.ok().body(agendaDTO);
  }

}
