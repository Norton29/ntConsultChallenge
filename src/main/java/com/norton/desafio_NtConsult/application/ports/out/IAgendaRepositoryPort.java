package com.norton.desafio_NtConsult.application.ports.out;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;

public interface IAgendaRepositoryPort {

  public Agenda save(Agenda agenda);

  public List<Agenda> find();

  public Agenda findById(Long id) ;

  public void registerAgenda(String description) throws Exception;

  public Agenda showResults(Long agendaId);

}
