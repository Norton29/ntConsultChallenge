package com.norton.desafio_NtConsult.application.core.usecase;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Async;

import com.norton.desafio_NtConsult.application.core.domain.Agenda;
import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.core.domain.CurrentPoll;
import com.norton.desafio_NtConsult.application.core.domain.Poll;
import com.norton.desafio_NtConsult.application.core.domain.Vote;
import com.norton.desafio_NtConsult.application.ports.in.IPollServicePort;
import com.norton.desafio_NtConsult.application.ports.out.IPollRepositoryPort;

public class PollService implements IPollServicePort {

  private final IPollRepositoryPort pollRepository;

  AgendaService agendaService;

  AssociatedService associatedService;

  private AtomicInteger voteYes = new AtomicInteger(0);
  private AtomicInteger voteNo = new AtomicInteger(0);
  private boolean openPoll = false;
  private Set<Long> associatedVoted = new HashSet<>();

  public PollService(IPollRepositoryPort pollRepository, AgendaService agendaService, AssociatedService associatedService) {
    this.pollRepository = pollRepository;
    this.agendaService = agendaService;
    this.associatedService = associatedService;
  }

  @Override
  public void startPoll(CurrentPoll currentPoll) {
    Agenda agenda = agendaService.findById(currentPoll.getAgenda().getId());
    if (agenda.isVoted()) {
      throw new IllegalStateException("Pauta já votada.");
    }
    openPoll();
    closePollAuto(currentPoll);
  }

  @Async
  public void closePollAuto(CurrentPoll currentPoll) {
    if (currentPoll.getMinutes() == null) {
      currentPoll.setMinutes(0);
    }
    Duration duration = Duration.ofMinutes(currentPoll.getMinutes());
    try {
      Thread.sleep(duration.toMillis() > 0 ? duration.toMillis() : 60000);
      closePoll(currentPoll);
      System.out.println("Sessão encerrada automaticamente.");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void registerVote(Vote vote) {
    if (!isPollOpen()) {
      throw new IllegalStateException("Sessão de votação encerrada.");
    }
    Associated associated = associatedService.findByCpf(vote.getAssociated().getCpf());
    if (associatedVoted.contains(associated.getId())) {
      throw new IllegalStateException("Associado já votou.");
    }
    associatedVoted.add(associated.getId());
    registerVote(vote.isVote());
  }

  public Poll showResults(Long pollId) {
    return pollRepository.showResults(pollId);
  }

  public void openPoll() {
    this.openPoll = true;
    associatedVoted.clear();
    this.voteYes.set(0);
    this.voteNo.set(0);
  }

  public void closePoll(CurrentPoll currentPoll)  {
    this.openPoll = false;

    pollRepository.save(Poll.builder()
        .agenda(currentPoll.getAgenda())
        .yesVotes(getYesVote())
        .noVotes(getNoVote())
        .minutes(currentPoll.getMinutes())
        .build());

    Agenda agenda = agendaService.findById(currentPoll.getAgenda().getId());
    if (agenda != null) {
      agenda.setNoVotes(getNoVote());
      agenda.setYesVotes(getYesVote());
      agenda.setVoted(true);
      agendaService.save(agenda);
    }
  }

  public void registerVote(boolean voto) {
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

  public List<Poll> find() {
    return pollRepository.find();    
  }

}
