package com.norton.desafio_NtConsult.infra.outbound.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norton.desafio_NtConsult.infra.inbound.model.AssociatedModel;

@Repository
public interface AssociatedRepository extends JpaRepository<AssociatedModel, Long> {

    Optional<AssociatedModel> findByCpf(String cpf);

    void deleteByCpf(String cpf);

    
    
}
