package com.norton.desafio_NtConsult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.dto.CurrentPollDTO;
import com.norton.desafio_NtConsult.service.PollService;

@RestController
@RequestMapping("/poll")
public class PollController {

    @Autowired
    PollService pollService;

    @PostMapping("/start")
    public ResponseEntity<String> startPoll(CurrentPollDTO currentPollDTO) {
        pollService.startPoll(currentPollDTO);
        return ResponseEntity.ok("Sessão de votação iniciada!");
    }

    @PostMapping("/voting")
    public ResponseEntity<String> vote(@RequestParam boolean voto) {
        try {
            pollService.registerVote(voto);
            return ResponseEntity.ok("Voto registrado com sucesso.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/results")
    public ResponseEntity<String> results() {
        return ResponseEntity.ok(pollService.showResults());
    }

    

}
