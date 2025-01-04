package com.norton.desafio_NtConsult.infra.outbound.adapters;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.norton.desafio_NtConsult.application.core.domain.Poll;
import com.norton.desafio_NtConsult.application.ports.out.IPollRepositoryPort;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;
import com.norton.desafio_NtConsult.infra.inbound.model.PollModel;
import com.norton.desafio_NtConsult.infra.outbound.repository.PollRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PollRepositoryAdapter implements IPollRepositoryPort {

  private PollRepository pollRepository;

  private Mappers mappers;

  @Override
  public Poll showResults(Long pollId) {
    PollModel pollModel = pollRepository.findById(pollId).orElse(null);
    if (pollModel == null) {
      throw new NoSuchElementException("Sessão de votação não encontrada.");
    }
    return mappers.pollModelToPoll(pollModel);

  }

  @Override
  public List<Poll> find() {
    return pollRepository.findAll().stream()
    .map(poll -> mappers.pollModelToPoll(poll))
        .collect(Collectors.toList());
    
  }

  @Override
  public Poll save(Poll poll) {
    PollModel pollModel = mappers.pollToPollModel(poll);
    return mappers.pollModelToPoll(pollRepository.save(pollModel));
  }

}
