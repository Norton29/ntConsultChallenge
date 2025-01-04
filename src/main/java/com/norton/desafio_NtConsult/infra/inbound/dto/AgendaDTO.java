package com.norton.desafio_NtConsult.infra.inbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AgendaDTO {

    private Long id;
    private String description;
    private Integer yesVotes;
    private Integer noVotes;
    private boolean voted;

}
