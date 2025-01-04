package com.norton.desafio_NtConsult.application.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Poll {

  private Long id;
  private Integer yesVotes;
  private Integer noVotes;
  private Agenda agenda;
  private Integer minutes;

}
