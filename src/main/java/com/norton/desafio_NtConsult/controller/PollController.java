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
import com.norton.desafio_NtConsult.dto.CurrentPollDTO;
import com.norton.desafio_NtConsult.dto.PollDTO;
import com.norton.desafio_NtConsult.dto.VoteDTO;
import com.norton.desafio_NtConsult.service.AgendaService;
import com.norton.desafio_NtConsult.service.PollService;

@RestController
@RequestMapping("/poll")
public class PollController {

  @Autowired
  PollService pollService;

  @Autowired
  AgendaService agendaService;

  @PostMapping("/start")
  public ResponseEntity<String> startPoll(@RequestBody CurrentPollDTO currentPollDTO) {
    pollService.startPoll(currentPollDTO);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/voting")
  public ResponseEntity<String> vote(@RequestBody VoteDTO voteDTO) {
    pollService.registerVote(voteDTO);
    return ResponseEntity.ok("Voto registrado com sucesso.");
  }

  @GetMapping("/results")
  public ResponseEntity<PollDTO> results(@RequestParam Long pollId) {
    PollDTO pollDTO = pollService.showResults(pollId);
    return ResponseEntity.ok().body(pollDTO);
  }

  @GetMapping
  public ResponseEntity<List<PollDTO>> allResults() {
    List<PollDTO> pollDTOs = pollService.showAllResults();
    return ResponseEntity.ok().body(pollDTOs);
  }
}
