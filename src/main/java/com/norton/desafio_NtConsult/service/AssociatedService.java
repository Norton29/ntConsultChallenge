package com.norton.desafio_NtConsult.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.norton.desafio_NtConsult.dto.AssociatedDTO;
import com.norton.desafio_NtConsult.model.Associated;
import com.norton.desafio_NtConsult.repository.AssociatedRepository;

@Service
public class AssociatedService {

  @Autowired
  AssociatedRepository associatedRepository;

  public void registerAssociated(AssociatedDTO associatedDTO) throws Exception {
    String cpf = associatedDTO.getCpf().replaceAll("[.\\-\\/]", "");
      if (associatedRepository.findByCpf(cpf).isPresent()) {
        throw new Exception("CPF já cadastrado.");
      }
      associatedRepository.save(Associated.builder()
          .name(associatedDTO.getName())
          .cpf(cpf)
          .build());
    
    ;
  }

  public AssociatedDTO getAssociatedById(Long id)  {
    try {
    Optional<Associated> associatedOptional = associatedRepository.findById(id);
    if(associatedOptional != null) {
      Associated associated = associatedOptional.get();
      return AssociatedDTO.builder()
      .name(associated.getName())
      .cpf(associated.getCpf())
      .build();
    }
  } catch (NoSuchElementException e) {
    throw new NoSuchElementException("Associado não encontrado: ");
  }
    return null;
  }

  public AssociatedDTO getAssociatedDTOByCpf(String cpf) {
    try {
    Optional<Associated> associatedOptional = associatedRepository.findByCpf(cpf);
    if (associatedOptional != null) {
      Associated associated = associatedOptional.get();
      return AssociatedDTO.builder()
          .name(associated.getName())
          .cpf(associated.getCpf())
          .build();
    }
  } catch (NoSuchElementException e) {
    throw new NoSuchElementException("Associado não encontrado: ");
  }
    return null;
  }

  public Associated getAssociatedByCpf(String cpf) {
    try {
    Optional<Associated> associatedOptional = associatedRepository.findByCpf(cpf);
    if (associatedOptional != null) {
      return associatedOptional.get();
    }
  } catch (NoSuchElementException e) {
    throw new NoSuchElementException("Associado não encontrado: ");
  }
    return null;
  }

  public List<AssociatedDTO> getAssociateds() {
    return associatedRepository.findAll().stream().map(associated -> {
      return AssociatedDTO.builder()
          .name(associated.getName())
          .cpf(associated.getCpf())
          .build();
    }).collect(Collectors.toList());
  }

  public void deleteAssociated(String cpf) {
    Optional<Associated> associated = associatedRepository.findByCpf(cpf);
    associatedRepository.deleteById(associated.get().getId());
  }

}
