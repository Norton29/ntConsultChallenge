package com.norton.desafio_NtConsult.application.ports.out;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Associated;

public interface IAssociatedRepositoryPort {

  public void registerAssociated(Associated associated) throws Exception;

  public Associated findById(Long id);

  public Associated findByCpf(String cpf);

  public void deleteAssociated(Long id);

  public List<Associated> find();

}
