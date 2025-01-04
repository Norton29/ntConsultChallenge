package com.norton.desafio_NtConsult.infra.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PollDTO {

    private Long id;
    private AgendaDTO agenda;
    private Integer minutes;

    
}
