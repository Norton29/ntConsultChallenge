package com.norton.desafio_NtConsult.infra.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class VoteDTO {

    private boolean vote;
    private AgendaDTO agenda;
    private AssociatedDTO associated;
    
}
