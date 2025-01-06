package com.norton.desafio_NtConsult.application.core.usecase;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.ports.in.IAssociatedServicePort;
import com.norton.desafio_NtConsult.application.ports.out.IAssociatedRepositoryPort;
import com.norton.desafio_NtConsult.infra.config.exceptions.ForbiddenException;

public class AssociatedService implements IAssociatedServicePort {

  private final IAssociatedRepositoryPort associatedRepository;

  public AssociatedService(IAssociatedRepositoryPort associatedRepository) {
    this.associatedRepository = associatedRepository;
  }

  @Override
  public void registerAssociated(Associated associated) throws Exception {
    String cpf = associated.getCpf().replaceAll("[.\\-\\/]", "");
    associated.setCpf(cpf);
      if (associatedRepository.find().stream().anyMatch(a -> a.getCpf().equals(associated.getCpf()))) {
        throw new ForbiddenException("CPF já cadastrado.");
      }
      associatedRepository.registerAssociated(associated);
  }

  @Override
  public Associated findByCpf(String cpf) {
    return associatedRepository.findByCpf(cpf);
  }

  @Override
  public List<Associated> find() {
    return associatedRepository.find();
  }

  @Override
  public void deleteAssociated(String cpf) {
    Associated associated = associatedRepository.findByCpf(cpf);
    associatedRepository.deleteAssociated(associated.getId());
  }

}
