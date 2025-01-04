package com.norton.desafio_NtConsult.infra.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norton.desafio_NtConsult.infra.inbound.model.PollModel;

@Repository
public interface PollRepository  extends JpaRepository<PollModel, Long> {

    
}
