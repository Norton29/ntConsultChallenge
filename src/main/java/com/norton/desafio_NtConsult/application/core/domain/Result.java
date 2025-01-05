package com.norton.desafio_NtConsult.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Result {

    private Agenda agenda;
    private Integer minutes;

}
