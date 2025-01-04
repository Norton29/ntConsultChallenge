package com.norton.desafio_NtConsult.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Associated {

  private Long id;
  private String name;
  private String cpf;
}
