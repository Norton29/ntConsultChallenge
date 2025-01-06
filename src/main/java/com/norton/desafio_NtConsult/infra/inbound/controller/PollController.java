package com.norton.desafio_NtConsult.infra.inbound.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.norton.desafio_NtConsult.application.core.domain.CurrentPoll;
import com.norton.desafio_NtConsult.application.core.domain.Poll;
import com.norton.desafio_NtConsult.application.core.domain.Vote;
import com.norton.desafio_NtConsult.application.core.usecase.PollService;
import com.norton.desafio_NtConsult.infra.inbound.dto.CurrentPollDTO;
import com.norton.desafio_NtConsult.infra.inbound.dto.PollDTO;
import com.norton.desafio_NtConsult.infra.inbound.dto.VoteDTO;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/poll")
@AllArgsConstructor
@Tag(name = "Votação")
public class PollController {

  private final PollService pollService;

  private final Mappers mappers;


  @Operation(summary = "Iniciar votação")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Votação iniciada com sucesso"),
      @ApiResponse(responseCode = "404", description = "Pauta já votada ou não encontrada"),
      @ApiResponse(responseCode = "500", description = "Erro ao processar a votação")
  })
  @PostMapping("/start")
  public ResponseEntity<String> startPoll(@RequestBody CurrentPollDTO currentPollDTO) throws JsonProcessingException {
    CurrentPoll currentPoll = mappers.currentPollDTOToCurrentPoll(currentPollDTO);
    pollService.startPoll(currentPoll);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Votar")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Voto registrado com sucesso"),
      @ApiResponse(responseCode = "403", description = "Associado já votou"),
      @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada ou já encerrada"),
      @ApiResponse(responseCode = "500", description = "Erro ao registrar voto")
  })
  @PostMapping("/voting")
  public ResponseEntity<String> vote(@RequestBody VoteDTO voteDTO) {
    Vote vote = mappers.voteDTOToVote(voteDTO);
    pollService.registerVote(vote);
    return ResponseEntity.ok("Voto registrado com sucesso.");
  }

  @Operation(summary = "Resultado da votação")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resultado da votação"),
      @ApiResponse(responseCode = "404", description = "Sessão de votação não encontrada"),
      @ApiResponse(responseCode = "500", description = "Erro Interno do servidor")
  })
  @GetMapping("/results")
  public ResponseEntity<PollDTO> results(@RequestParam Long pollId) {
    Poll showResults = pollService.showResults(pollId);
    PollDTO pollDTO = mappers.pollToPollDTO(showResults);
    return ResponseEntity.ok().body(pollDTO);
  }

  @Operation(summary = "Listar resultados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resultados listados"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  public ResponseEntity<List<PollDTO>> allResults() {
    List<Poll> list = pollService.find();
    List<PollDTO> pollDTOs = mappers.pollToPollDTO(list);
    return ResponseEntity.ok().body(pollDTOs);
  }
}
