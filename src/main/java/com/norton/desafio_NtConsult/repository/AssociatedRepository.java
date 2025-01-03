package com.norton.desafio_NtConsult.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norton.desafio_NtConsult.model.Associated;

@Repository
public interface AssociatedRepository extends JpaRepository<Associated, Long> {

    Optional<Associated> findByCpf(String cpf);

    void deleteByCpf(String cpf);

    
    
}
