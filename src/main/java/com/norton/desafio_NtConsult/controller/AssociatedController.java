package com.norton.desafio_NtConsult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.dto.AssociatedDTO;
import com.norton.desafio_NtConsult.service.AssociatedService;




@RestController
@RequestMapping("/associated")
public class AssociatedController {

    @Autowired
    AssociatedService associatedService;

    
    @PostMapping    
    public ResponseEntity<String> registerAssociated(@RequestBody AssociatedDTO associatedDTO) {
        try {
            associatedService.registerAssociated(associatedDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Associado registrado com sucesso.");
    }


    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AssociatedDTO> getByCpf(@PathVariable String cpf) throws Exception {
        AssociatedDTO associatedByCpf = associatedService.getAssociatedDTOByCpf(cpf);
        return ResponseEntity.ok(associatedByCpf);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociatedDTO> getById(@PathVariable Long id) throws Exception {
        AssociatedDTO associatedById = associatedService.getAssociatedById(id);
        return ResponseEntity.ok(associatedById);
    }

    @GetMapping
    public ResponseEntity<List<AssociatedDTO>> getAll() {
        List<AssociatedDTO> associatedById = associatedService.getAssociateds();
        return ResponseEntity.ok(associatedById);
    }
    

    @DeleteMapping("/{cpf}")
    public ResponseEntity<AssociatedDTO> deleteById(@PathVariable String cpf) {
        associatedService.deleteAssociated(cpf);
        return ResponseEntity.ok().build();
    }
    
}
