package com.norton.desafio_NtConsult.service;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.norton.desafio_NtConsult.dto.AgendaDTO;
import com.norton.desafio_NtConsult.dto.CurrentPollDTO;
import com.norton.desafio_NtConsult.dto.PollDTO;
import com.norton.desafio_NtConsult.dto.VoteDTO;
import com.norton.desafio_NtConsult.model.Agenda;
import com.norton.desafio_NtConsult.model.Associated;
import com.norton.desafio_NtConsult.model.Poll;
import com.norton.desafio_NtConsult.repository.AgendaRepository;
import com.norton.desafio_NtConsult.repository.PollRepository;

@Service
public class PollService {

  @Autowired
  PollRepository pollRepository;

  @Autowired
  AgendaService agendaService;

  @Autowired
  AssociatedService associatedService;

  private AtomicInteger voteYes = new AtomicInteger(0);
  private AtomicInteger voteNo = new AtomicInteger(0);
  private boolean openPoll = false;
  private Set<Long> associatedVoted = new HashSet<>();

  public void startPoll(CurrentPollDTO currentPollDTO) {
    Agenda agenda = agendaService.getById(currentPollDTO.getAgenda().getId());
    if (agenda.isVoted()) {
      throw new IllegalStateException("Pauta já votada.");
    }
    openPoll();
    System.out.println("Sessão de votação aberta!");
    closePollAuto(currentPollDTO);
  }

  @Async
  public void closePollAuto(CurrentPollDTO currentPollDTO) {
    if (currentPollDTO.getMinutes() == null) {
      currentPollDTO.setMinutes(0);
    }
    Duration duration = Duration.ofMinutes(currentPollDTO.getMinutes());
    try {
      Thread.sleep(duration.toMillis() > 0 ? duration.toMillis() : 60000);
      closePoll(currentPollDTO);
      System.out.println("Sessão encerrada automaticamente.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void registerVote(VoteDTO voteDTO) {
    if (!isPollOpen()) {
      throw new IllegalStateException("Sessão de votação encerrada.");
    }
    Associated associated = associatedService.getAssociatedByCpf(voteDTO.getAssociated().getCpf());
    if (associatedVoted.contains(associated.getId())) {
      throw new IllegalStateException("Associado já votou.");
    }
    associatedVoted.add(associated.getId());
    registreVote(voteDTO.isVote());
  }

  public PollDTO showResults(Long pollId) {
    Optional<Poll> pollOptional = pollRepository.findById(pollId);
    if (pollOptional != null) {
      Poll poll = pollOptional.get();
      return PollDTO.builder()
            .id(poll.getId())
            .agenda(AgendaDTO.builder()
                  .id(poll.getAgenda().getId())
                    .description(poll.getAgenda().getDescription())
                    .yesVotes(poll.getAgenda().getYes())
                    .noVotes(poll.getAgenda().getNo())
                    .voted(poll.getAgenda().isVoted())
                    .build())
            .minutes(poll.getMinutes())
            .build();
    }
    throw new NoSuchElementException("Sessão de votação não encontrada.");

  }

  public void openPoll() {
    this.openPoll = true;
    associatedVoted.clear();
    this.voteYes.set(0);
    this.voteNo.set(0);
  }

  public void closePoll(CurrentPollDTO currentPollDTO) {
    this.openPoll = false;

    pollRepository.save(Poll.builder()
        .agenda(currentPollDTO.getAgenda())
        .yesVotes(getYesVote())
        .noVotes(getNoVote())
        .minutes(currentPollDTO.getMinutes())
        .build());

    Agenda agenda = agendaService.getById(currentPollDTO.getAgenda().getId());
    if (agenda != null) {
      agenda.setNo(getNoVote());
      agenda.setYes(getYesVote());
      agenda.setVoted(true);
      agendaService.save(agenda);
    }
  }

  public void registreVote(boolean voto) {
    if (voto == true) {
      voteYes.incrementAndGet();
    } else if (voto == false) {
      voteNo.incrementAndGet();
    }
  }

  public int getYesVote() {
    return voteYes.get();
  }

  public int getNoVote() {
    return voteNo.get();
  }

  public boolean isPollOpen() {
    return openPoll;
  }

  public List<PollDTO> showAllResults() {
    List<Poll> polls = pollRepository.findAll();
    List<PollDTO> pollsDtos = polls.stream().map(poll -> {
        return PollDTO.builder()
            .id(poll.getId())
            .agenda(AgendaDTO.builder()
                  .id(poll.getAgenda().getId())
                    .description(poll.getAgenda().getDescription())
                    .yesVotes(poll.getAgenda().getYes())
                    .noVotes(poll.getAgenda().getNo())
                    .voted(poll.getAgenda().isVoted())
                    .build())
            .minutes(poll.getMinutes())
            .build();
      }).collect(Collectors.toList());
  
    return pollsDtos;
  }

}
