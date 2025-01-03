package com.norton.desafio_NtConsult.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgendaDTO {

    private Long id;
    private String description;
    private Integer yesVotes;
    private Integer noVotes;
    private boolean voted;

}
