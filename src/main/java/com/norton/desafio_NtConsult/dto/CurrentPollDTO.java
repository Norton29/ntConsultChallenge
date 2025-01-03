package com.norton.desafio_NtConsult.dto;

import com.norton.desafio_NtConsult.model.Agenda;

import lombok.Data;

@Data
public class CurrentPollDTO {
    
    private Agenda agenda;
    private Integer minutes;
}
