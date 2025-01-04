package com.norton.desafio_NtConsult.application.ports.in;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;

public interface IAgendaServicePort {

    public Agenda save(Agenda agenda);

    public List<Agenda> find();
    
    public Agenda findById(Long id);

    public void registerAgenda(String description);

    public Agenda showResults(Long agendaId);

}
