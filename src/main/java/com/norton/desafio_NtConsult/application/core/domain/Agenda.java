package com.norton.desafio_NtConsult.application.core.domain;

import lombok.Data;

@Data
public class Agenda {
    
    private Long id;
    private String description;
    private Integer yesVotes;
    private Integer noVotes;
    private boolean voted;
}
