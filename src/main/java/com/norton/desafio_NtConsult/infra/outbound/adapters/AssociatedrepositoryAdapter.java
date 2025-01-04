package com.norton.desafio_NtConsult.infra.outbound.adapters;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.ports.out.IAssociatedRepositoryPort;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;
import com.norton.desafio_NtConsult.infra.inbound.model.AssociatedModel;
import com.norton.desafio_NtConsult.infra.outbound.repository.AssociatedRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AssociatedrepositoryAdapter implements IAssociatedRepositoryPort {

  private AssociatedRepository associatedRepository;

  private Mappers mappers;

  @Override
  public void registerAssociated(Associated associated) throws Exception {
    AssociatedModel associatedModel = mappers.associatedToAssociatedModel(associated);
    associatedRepository.save(associatedModel);
  }

  @Override
  public Associated findById(Long id) {
    try {
      Optional<AssociatedModel> associatedOptional = associatedRepository.findById(id);
      if (associatedOptional != null) {
        AssociatedModel associated = associatedOptional.get();
        return mappers.associatedModelToAssociated(associated);
      }
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("Associado não encontrado: ");
    }
    return null;
  }

  @Override
  public Associated findByCpf(String cpf) {
    try {
      Optional<AssociatedModel> associatedOptional = associatedRepository.findByCpf(cpf);
      if (associatedOptional != null) {
        AssociatedModel associated = associatedOptional.get();
        return mappers.associatedModelToAssociated(associated);
      }
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("Associado não encontrado: ");
    }
    return null;
  }

  @Override
  public void deleteAssociated(Long id) {
    associatedRepository.deleteById(id);
  }

  @Override
  public List<Associated> find() {
    return associatedRepository.findAll().stream()
    .map(associated -> mappers.associatedModelToAssociated(associated))
        .collect(Collectors.toList());
  }

}
