package com.norton.desafio_NtConsult.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CurrentPoll {

  private Agenda agenda;
  private Integer minutes;
}
