package com.norton.desafio_NtConsult.infra.inbound.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Component
@Entity
@Table(name = "poll")
public class PollModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer yesVotes;
    private Integer noVotes;

    @OneToOne
    @JoinColumn(name = "agenda_id", unique = true)
    private AgendaModel agenda;
    private Integer minutes;
    
 
    

    

    
}
