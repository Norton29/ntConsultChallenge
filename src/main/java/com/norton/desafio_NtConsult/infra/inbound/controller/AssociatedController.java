package com.norton.desafio_NtConsult.infra.inbound.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.ports.in.IAssociatedServicePort;
import com.norton.desafio_NtConsult.infra.inbound.dto.AssociatedDTO;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;

import lombok.AllArgsConstructor;




@RestController
@RequestMapping("/v1/associated")
@AllArgsConstructor
public class AssociatedController {

    private final IAssociatedServicePort associatedService;

    private final Mappers mappers;
    
    @PostMapping    
    public ResponseEntity<String> registerAssociated(@RequestBody AssociatedDTO associatedDTO) throws Exception {
        Associated associated = mappers.associatedDTOToAssociated(associatedDTO);
        associatedService.registerAssociated(associated);
        return ResponseEntity.ok("Associado registrado com sucesso.");
    }


    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AssociatedDTO> getByCpf(@PathVariable String cpf) throws Exception {
        Associated associated = associatedService.findByCpf(cpf);
        AssociatedDTO associatedDto = mappers.associatedToAssociatedDTO(associated);
        return ResponseEntity.ok(associatedDto);
    }

    @GetMapping
    public ResponseEntity<List<AssociatedDTO>> getAll() {
        List<Associated> list = associatedService.find();
        List<AssociatedDTO> associatedDTOs = mappers.associatedToAssociatedDTO(list);
        return ResponseEntity.ok(associatedDTOs);
    }
    

    @DeleteMapping("/{cpf}")
    public ResponseEntity<AssociatedDTO> deleteById(@PathVariable String cpf) {
        associatedService.deleteAssociated(cpf);
        return ResponseEntity.ok().build();
    }
    
}
