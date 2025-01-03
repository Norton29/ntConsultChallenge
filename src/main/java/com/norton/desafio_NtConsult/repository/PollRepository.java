package com.norton.desafio_NtConsult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norton.desafio_NtConsult.model.Poll;

@Repository
public interface PollRepository  extends JpaRepository<Poll, Long> {

    
}
