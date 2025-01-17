package com.norton.desafio_NtConsult.infra.inbound.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter@AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "associated")
public class AssociatedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) 
    private Long id;
    private String name;
    private String cpf;
    
}
