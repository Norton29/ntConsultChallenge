package com.norton.desafio_NtConsult.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.norton.desafio_NtConsult.dto.AgendaDTO;
import com.norton.desafio_NtConsult.model.Agenda;
import com.norton.desafio_NtConsult.repository.AgendaRepository;

@Service
public class AgendaService {

  @Autowired
  AgendaRepository agendaRepository;

  public Agenda save(Agenda agenda) {
    return agendaRepository.save(agenda);
  }

  public List<AgendaDTO> getAgendas() {
    return agendaRepository.findAll().stream().map(agenda -> {
      return AgendaDTO.builder()
      .id(agenda.getId())
      .description(agenda.getDescription())
      .yesVotes(agenda.getYes())
      .noVotes(agenda.getNo())
      .voted(agenda.isVoted())
      .build();
    }).collect(Collectors.toList());
  }

  public Agenda getById(Long id) {
    try {
      Agenda agenda = agendaRepository.findById(id).get();
      if(agenda != null) {
        return agenda;
      }
    } catch (Exception e) {
      throw new NoSuchElementException("Pauta não encontrada.");
    }
    return null;
  }

  public void registerAgenda(String description) {
    agendaRepository.save(Agenda.builder()
    .description(description)
    .voted(false)
    .build());
  }

  public AgendaDTO showResults(Long agendaId) {
    Agenda agenda = getById(agendaId);
      if (!agenda.isVoted()) {
        throw new IllegalStateException("Pauta ainda não votada.");
      }
      return AgendaDTO.builder()
      .id(agendaId)
      .description(agenda.getDescription())
      .yesVotes(agenda.getYes())
      .noVotes(agenda.getNo())
      .voted(agenda.isVoted())
      .build();
  }
}
