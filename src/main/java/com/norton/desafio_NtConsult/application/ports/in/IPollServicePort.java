package com.norton.desafio_NtConsult.application.ports.in;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.CurrentPoll;
import com.norton.desafio_NtConsult.application.core.domain.Poll;
import com.norton.desafio_NtConsult.application.core.domain.Vote;

public interface IPollServicePort {

    public void startPoll(CurrentPoll currentPoll);

    public void registerVote(Vote vote);

    public Poll showResults(Long pollId);

    public List<Poll> find();
    
}
