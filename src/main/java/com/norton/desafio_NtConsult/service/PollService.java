package com.norton.desafio_NtConsult.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.norton.desafio_NtConsult.dto.CurrentPollDTO;
import com.norton.desafio_NtConsult.model.Poll;

@Service
public class PollService {

    @Autowired
    Poll poll;

    public void startPoll(CurrentPollDTO currentPollDTO) {
        Duration duration = Duration.ofMinutes(currentPollDTO.getMinutes());
        poll.openPoll();
        System.out.println("Sessão de votação aberta!");

        closePollAuto(duration);
    }

    @Async
    public void closePollAuto(Duration duration) {
        try {
            Thread.sleep(duration.toMillis() > 0 ? duration.toMillis() : 60000); 
            poll.closePoll();
            System.out.println("Sessão encerrada automaticamente.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void registerVote(boolean voto) {
        if (!poll.isPollOpen()) {
            throw new IllegalStateException("Sessão de votação encerrada.");
        }
        poll.registreVote(voto);
    }

    public String showResults() {
        return "Sim: " + poll.getYesVote() +
               ", Não: " + poll.getNoVote();
    }
    
}
