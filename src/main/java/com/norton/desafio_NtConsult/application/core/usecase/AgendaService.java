package com.norton.desafio_NtConsult.application.core.usecase;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;
import com.norton.desafio_NtConsult.application.ports.in.IAgendaServicePort;
import com.norton.desafio_NtConsult.application.ports.out.IAgendaRepositoryPort;

public class AgendaService implements IAgendaServicePort {

  private final IAgendaRepositoryPort agendaRepository;

  public AgendaService(IAgendaRepositoryPort agendaRepository) {
    this.agendaRepository = agendaRepository;
  }

  @Override
  public Agenda save(Agenda agenda) {
    return agendaRepository.save(agenda);
  }

  @Override
  public List<Agenda> find() {
    return agendaRepository.find();
  }

  @Override
  public Agenda findById(Long id) {
    return agendaRepository.findById(id);
  }

  @Override
  public void registerAgenda(String description) {
    agendaRepository.registerAgenda(description);
  }

  @Override
  public Agenda showResults(Long agendaId) {
    return agendaRepository.showResults(agendaId);
  }
}
