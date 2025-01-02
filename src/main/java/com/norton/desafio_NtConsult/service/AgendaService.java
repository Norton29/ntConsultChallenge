package com.norton.desafio_NtConsult.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.norton.desafio_NtConsult.dto.AgendaDTO;
import com.norton.desafio_NtConsult.model.Agenda;
import com.norton.desafio_NtConsult.repository.AgendaRepository;

public class AgendaService {

    @Autowired
    AgendaRepository agendaRepository;
    

    public List<AgendaDTO> getAgendas() {
        return agendaRepository.findAll().stream().map(agenda -> {
            AgendaDTO agendaDTO = new AgendaDTO();
            agendaDTO.setId(agenda.getId());
            agendaDTO.setDescription(agenda.getDescription());
            return agendaDTO;
        }).collect(Collectors.toList());
    }


    public void registerAgenda(String description) {
        agendaRepository.save(Agenda.builder().description(description).build());
    }
}
