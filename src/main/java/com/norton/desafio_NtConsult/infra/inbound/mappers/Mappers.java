package com.norton.desafio_NtConsult.infra.inbound.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;
import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.core.domain.CurrentPoll;
import com.norton.desafio_NtConsult.application.core.domain.Poll;
import com.norton.desafio_NtConsult.application.core.domain.Vote;
import com.norton.desafio_NtConsult.infra.inbound.dto.AgendaDTO;
import com.norton.desafio_NtConsult.infra.inbound.dto.AssociatedDTO;
import com.norton.desafio_NtConsult.infra.inbound.dto.CurrentPollDTO;
import com.norton.desafio_NtConsult.infra.inbound.dto.PollDTO;
import com.norton.desafio_NtConsult.infra.inbound.dto.VoteDTO;
import com.norton.desafio_NtConsult.infra.inbound.model.AgendaModel;
import com.norton.desafio_NtConsult.infra.inbound.model.AssociatedModel;
import com.norton.desafio_NtConsult.infra.inbound.model.PollModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Mappers {
    
    private ModelMapper modelMapper;

    public List<AgendaDTO> agendaToAgendaDTO(List<Agenda> list) {
        return list.stream()
                .map(agenda -> modelMapper.map(agenda, AgendaDTO.class))
                .collect(Collectors.toList());
    }

    public AgendaDTO agendaToAgendaDTO(Agenda agenda) {
        return modelMapper.map(agenda, AgendaDTO.class);
    }

    public Associated associatedDTOToAssociated(AssociatedDTO associatedDTO) {
        return modelMapper.map(associatedDTO, Associated.class);
    }

    public AssociatedDTO associatedToAssociatedDTO(Associated associated) {
        return modelMapper.map(associated, AssociatedDTO.class);
    }

    public List<AssociatedDTO> associatedToAssociatedDTO(List<Associated> list) {
        return list.stream()
                .map(associated -> modelMapper.map(associated, AssociatedDTO.class))
                .collect(Collectors.toList());
    }

    public CurrentPoll currentPollDTOToCurrentPoll(CurrentPollDTO currentPollDTO) {
        return modelMapper.map(currentPollDTO, CurrentPoll.class);
    }

    public Vote voteDTOToVote(VoteDTO voteDTO) {
        return modelMapper.map(voteDTO, Vote.class);
    }

    public PollDTO pollToPollDTO(Poll showResults) {
        return modelMapper.map(showResults, PollDTO.class);
    }

    public List<PollDTO> pollToPollDTO(List<Poll> list) {
        return list.stream()
                .map(poll -> modelMapper.map(poll, PollDTO.class))
                .collect(Collectors.toList());
    }

    public AgendaModel agendaToAgendaModel(Agenda agenda) {
        return modelMapper.map(agenda, AgendaModel.class);
    }

    public Agenda agendaModelToAgenda(AgendaModel agendaModel) {
        return modelMapper.map(agendaModel, Agenda.class);
    }

    public AssociatedModel associatedToAssociatedModel(Associated associated) {
        return modelMapper.map(associated, AssociatedModel.class);
    }

    public Associated associatedModelToAssociated(AssociatedModel associated) {
        return modelMapper.map(associated, Associated.class);
    }

    public Poll pollModelToPoll(PollModel poll) {
        return modelMapper.map(poll, Poll.class);
    }

    public PollModel pollToPollModel(Poll poll) {
        return modelMapper.map(poll, PollModel.class);
    }

    

}
