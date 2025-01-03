package com.norton.desafio_NtConsult.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PollDTO {

    private Long id;
    private AgendaDTO agenda;
    private Integer minutes;

    
}
