package com.norton.desafio_NtConsult.application.core.domain;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CurrentPoll {

  private Agenda agenda;
  private Integer minutes;
  private boolean openPoll;
  private Integer yes;
  private Integer no;
  private Set<Long> associatedVoted;
}
