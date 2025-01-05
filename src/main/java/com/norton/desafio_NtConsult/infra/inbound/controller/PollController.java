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

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/poll")
@AllArgsConstructor
public class PollController {

  private final PollService pollService;

  private final Mappers mappers;


  @PostMapping("/start")
  public ResponseEntity<String> startPoll(@RequestBody CurrentPollDTO currentPollDTO) throws JsonProcessingException {
    CurrentPoll currentPoll = mappers.currentPollDTOToCurrentPoll(currentPollDTO);
    pollService.startPoll(currentPoll);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/voting")
  public ResponseEntity<String> vote(@RequestBody VoteDTO voteDTO) {
    Vote vote = mappers.voteDTOToVote(voteDTO);
    pollService.registerVote(vote);
    return ResponseEntity.ok("Voto registrado com sucesso.");
  }

  @GetMapping("/results")
  public ResponseEntity<PollDTO> results(@RequestParam Long pollId) {
    Poll showResults = pollService.showResults(pollId);
    PollDTO pollDTO = mappers.pollToPollDTO(showResults);
    return ResponseEntity.ok().body(pollDTO);
  }

  @GetMapping
  public ResponseEntity<List<PollDTO>> allResults() {
    List<Poll> list = pollService.find();
    List<PollDTO> pollDTOs = mappers.pollToPollDTO(list);
    return ResponseEntity.ok().body(pollDTOs);
  }
}
