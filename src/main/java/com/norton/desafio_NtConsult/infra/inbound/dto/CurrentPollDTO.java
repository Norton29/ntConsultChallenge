package com.norton.desafio_NtConsult.infra.inbound.dto;

import com.norton.desafio_NtConsult.infra.inbound.model.AgendaModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CurrentPollDTO {
    
    private AgendaModel agenda;
    private Integer minutes;
}
