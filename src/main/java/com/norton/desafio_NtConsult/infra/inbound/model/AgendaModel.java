package com.norton.desafio_NtConsult.infra.inbound.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "agenda")
public class AgendaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private String description;
    private Integer yes;
    private Integer no;
    @NotNull
    private boolean voted;
    
    @OneToOne(mappedBy = "agenda", cascade = CascadeType.ALL)
    private PollModel poll;
    
    
}
