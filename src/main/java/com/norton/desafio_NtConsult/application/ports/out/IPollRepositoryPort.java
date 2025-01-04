package com.norton.desafio_NtConsult.application.ports.out;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Poll;

public interface IPollRepositoryPort {

  public Poll showResults(Long pollId);

  public List<Poll> find();

  public Poll save(Poll poll);


}
