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
        try {
            AgendaModel agendaModel = agendaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pauta não encontrada."));
        return mappers.agendaModelToAgenda(agendaModel);
        } catch (Exception e) {
            System.out.println("Mensagem da exceção: " + e.getMessage());
        throw e;
        }
        
    }

    @Override
    public void registerAgenda(String description) {
        agendaRepository.save(AgendaModel.builder()
                .description(description)
                .voted(false)
                .build());
    }

    @Override
    public Agenda showResults(Long agendaId) {
        AgendaModel agendaModel = agendaRepository.findById(agendaId).get();
        if (!agendaModel.isVoted()) {
            throw new IllegalStateException("Pauta ainda não votada.");
        }
        return mappers.agendaModelToAgenda(agendaModel);
    }

}
