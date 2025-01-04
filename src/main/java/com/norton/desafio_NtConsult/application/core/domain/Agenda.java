package com.norton.desafio_NtConsult.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Agenda {
    
    private Long id;
    private String description;
    private Integer yes;
    private Integer no;
    private boolean voted;
}
