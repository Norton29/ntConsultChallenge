package com.norton.desafio_NtConsult.infra.inbound.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/agenda")
@AllArgsConstructor
@Tag(name = "Pauta")
public class AgendaController {

  private final  IAgendaServicePort agendaService;

  private final Mappers mappers;

  @Operation(summary = "Listar todas as pautas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Listagem de pautas realizada com sucesso"),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
  })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AgendaDTO>> agendas() {
    List<Agenda> list = agendaService.find();
   List<AgendaDTO> agendasDTOs = list.stream().map(agenda -> mappers.agendaToAgendaDTO(agenda))
        .collect(Collectors.toList());
    return ResponseEntity.ok(agendasDTOs);
  }


  @Operation(summary = "Registrar pauta")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Pauta registrada com sucesso"),
      @ApiResponse(responseCode = "500", description = "Erro ao registrar pauta")
  })
  @SuppressWarnings("rawtypes")
  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity registerAgenda(@RequestBody AgendaDTO agenda) throws Exception {
    agendaService.registerAgenda(agenda.getDescription());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  @Operation(summary = "Buscar resultados da pauta pelo seu Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resultados da pauta encontrados com sucesso"),
      @ApiResponse(responseCode = "400", description = "Pauta Ainda não votada"),
      @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
      @ApiResponse(responseCode = "500", description = "Erro ao buscar resultados da pauta")
  })
  @GetMapping(value = "/results", produces = "application/json")
  public ResponseEntity<AgendaDTO> results(@RequestParam Long agendaId) {
    Agenda agenda = agendaService.showResults(agendaId);
    AgendaDTO agendaDTO = mappers.agendaToAgendaDTO(agenda);
    return ResponseEntity.ok().body(agendaDTO);
  }

}
