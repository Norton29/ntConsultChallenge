package com.norton.desafio_NtConsult.application.core.usecase;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.norton.desafio_NtConsult.application.core.domain.Agenda;
import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.core.domain.CurrentPoll;
import com.norton.desafio_NtConsult.application.core.domain.Poll;
import com.norton.desafio_NtConsult.application.core.domain.Result;
import com.norton.desafio_NtConsult.application.core.domain.Vote;
import com.norton.desafio_NtConsult.application.ports.in.IPollServicePort;
import com.norton.desafio_NtConsult.application.ports.out.ICPFValidatorPort;
import com.norton.desafio_NtConsult.application.ports.out.IPollRepositoryPort;
import com.norton.desafio_NtConsult.application.ports.out.IResultQueuePort;
import com.norton.desafio_NtConsult.infra.config.exceptions.ForbiddenException;
import com.norton.desafio_NtConsult.infra.config.exceptions.GenericException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class PollService implements IPollServicePort {

  private final IPollRepositoryPort pollRepository;

  private final ICPFValidatorPort cpfValidator;

  private final IResultQueuePort resultQueue;

  AgendaService agendaService;

  AssociatedService associatedService;
  
  public PollService(IPollRepositoryPort pollRepository, AgendaService agendaService, AssociatedService associatedService, 
  ICPFValidatorPort cpfValidator, IResultQueuePort resultQueue) {
    this.pollRepository = pollRepository;
    this.agendaService = agendaService;
    this.associatedService = associatedService;
    this.cpfValidator = cpfValidator;
    this.resultQueue = resultQueue;
  }


  private final ConcurrentHashMap<Long, CurrentPoll> activePolls = new ConcurrentHashMap<>(); 

  @Override
  public void startPoll(CurrentPoll currentPoll) {
    Agenda agenda = agendaService.findById(currentPoll.getAgenda().getId());
    if (agenda.isVoted()) {
      throw new GenericException("Pauta já votada.");
    }
    openPoll(currentPoll);
    activePolls.put(agenda.getId(), currentPoll);
    CompletableFuture.runAsync(() -> {
      try {
        closePollAuto(currentPoll);

      }catch (JsonProcessingException e){
        throw new GenericException("Erro ao enviar o resultado.", e);
      } catch (Exception e) {
        throw new GenericException("Erro ao encerrar sessão de votação.", e);
      }
      });
      
  }

  @Async
  public void closePollAuto(CurrentPoll currentPoll) throws JsonProcessingException {
    if (currentPoll.getMinutes() == null) {
      currentPoll.setMinutes(0);
    }
    Duration duration = Duration.ofMinutes(currentPoll.getMinutes());
    try {
      Thread.sleep(duration.toMillis() > 0 ? duration.toMillis() : 60000);
      closePoll(currentPoll);
      System.out.println("Sessão encerrada automaticamente.");
    } catch (InterruptedException e) {
      throw new GenericException("Erro ao encerrar sessão de votação.", e);
    }
  }

  public void registerVote(Vote vote) {
    CurrentPoll currentPoll = activePolls.get(vote.getAgenda().getId());
    if (currentPoll == null || !currentPoll.isOpenPoll()) {
      throw new NoSuchElementException("Sessão de votação não encontrada ou já encerrada.");
    }
    Associated associated = associatedService.findByCpf(vote.getAssociated().getCpf());
    if (currentPoll.getAssociatedVoted().contains(associated.getId())) {
      throw new ForbiddenException("Associado já votou.");
    }
    // if(!cpfValidator.isValid(vote.getAssociated().getCpf())) {
    //   throw new GenericException("CPF inválido.");
    // }
    currentPoll.getAssociatedVoted().add(associated.getId());
    registerVote(currentPoll, vote.isVote());
  }

  public void openPoll(CurrentPoll currentPoll) {
    currentPoll.setOpenPoll(true);
    currentPoll.setYes(0);
    currentPoll.setNo(0);
    currentPoll.setAssociatedVoted(new HashSet<>());
  }

  public void closePoll(CurrentPoll currentPoll) throws JsonProcessingException  {
    synchronized(currentPoll){
      System.out.println("Encerrando sessão de votação para: " + currentPoll.getAgenda().getId());
      currentPoll.setOpenPoll(false);
    
      Agenda agenda = agendaService.findById(currentPoll.getAgenda().getId());
      if (agenda != null) {
        agenda.setNo(currentPoll.getNo());
        agenda.setYes(currentPoll.getYes());
        agenda.setVoted(true);
        agendaService.save(agenda);
      }
      
      resultQueue.sendResult(Result.builder()
      .agenda(agenda)
      .minutes(currentPoll.getMinutes())
      .build());
      
      activePolls.remove(currentPoll.getAgenda().getId());
    }

  }

  public void registerVote(CurrentPoll currentPoll, boolean voto) {
    synchronized (currentPoll) {
      if (voto) {
        currentPoll.setYes(currentPoll.getYes() + 1);
      } else {
        currentPoll.setNo(currentPoll.getNo() + 1);
      }
      Poll poll = pollRepository.findByAgendaId(currentPoll);
      if(poll == null ){
        pollRepository.save(Poll.builder()
        .agenda(currentPoll.getAgenda())
        .yesVotes(currentPoll.getYes())
        .noVotes(currentPoll.getNo())
        .minutes(currentPoll.getMinutes())
        .build());
      }else{
        poll.setYesVotes(currentPoll.getYes());
        poll.setNoVotes(currentPoll.getNo());
        pollRepository.save(poll);
      }
    }
  }

  public List<Poll> find() {
    return pollRepository.find();    
  }
  
  public Poll showResults(Long pollId) {
    return pollRepository.showResults(pollId);
  }

}
