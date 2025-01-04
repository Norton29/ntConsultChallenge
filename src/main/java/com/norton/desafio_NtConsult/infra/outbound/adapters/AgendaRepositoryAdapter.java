package com.norton.desafio_NtConsult.infra.outbound.adapters;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;
import com.norton.desafio_NtConsult.application.ports.out.IAgendaRepositoryPort;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;
import com.norton.desafio_NtConsult.infra.inbound.model.AgendaModel;
import com.norton.desafio_NtConsult.infra.outbound.repository.AgendaRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AgendaRepositoryAdapter implements IAgendaRepositoryPort {

  private AgendaRepository agendaRepository;

  private Mappers mappers;

  @Override
  public Agenda save(Agenda agenda) {
    AgendaModel agendaModel = mappers.agendaToAgendaModel(agenda);
    agendaRepository.save(agendaModel);
    return mappers.agendaModelToAgenda(agendaModel);

  }

  @Override
  public List<Agenda> find() {
    return agendaRepository.findAll().stream().map(agenda -> mappers.agendaModelToAgenda(agenda))
        .collect(Collectors.toList());
  }

  @Override
  public Agenda findById(Long id) {
    AgendaModel agendaModel = agendaRepository.findById(id).orElse(null);
    if (agendaModel == null) {
      throw new NoSuchElementException("Pauta não encontrada.");
    }
    return mappers.agendaModelToAgenda(agendaModel);
  }

  @Override
  public void registerAgenda(String description) throws Exception {
    try {
      agendaRepository.save(AgendaModel.builder()
        .description(description)
        .voted(false)
        .build());
    } catch (Exception e) {
      throw new Exception("Erro ao registrar pauta.");
    }
    
  }

  @Override
  public Agenda showResults(Long agendaId) {
    AgendaModel agendaModel = agendaRepository.findById(agendaId).orElse(null);
    if (agendaModel == null) {
      throw new NoSuchElementException("Pauta não encontrada.");
    }
    return mappers.agendaModelToAgenda(agendaModel);
  }

}
