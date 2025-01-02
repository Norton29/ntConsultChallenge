package com.norton.desafio_NtConsult.model;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Agenda agenda;
    private AtomicInteger voteYes = new AtomicInteger(0);
    private AtomicInteger voteNo = new AtomicInteger(0);
    private boolean openPoll = false;
    

    public void openPoll() {
        this.openPoll = true;
        this.voteYes.set(0);
        this.voteNo.set(0);
    }

    public void closePoll() {
        this.openPoll = false;
    }

    public void registreVote(boolean voto) {
        if (voto == true) {
            voteYes.incrementAndGet();
        } else if (voto == false)  {
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
}
