package com.norton.desafio_NtConsult.infra.inbound.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norton.desafio_NtConsult.application.core.domain.Associated;
import com.norton.desafio_NtConsult.application.ports.in.IAssociatedServicePort;
import com.norton.desafio_NtConsult.infra.inbound.dto.AssociatedDTO;
import com.norton.desafio_NtConsult.infra.inbound.mappers.Mappers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/associated")
@AllArgsConstructor
@Tag(name = "Associado")
public class AssociatedController {

    private final IAssociatedServicePort associatedService;

    private final Mappers mappers;
    
    @Operation(summary = "Registrar associado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Associado registrado com sucesso"),
        @ApiResponse(responseCode = "403", description = "CPF já cadastrado"),
        @ApiResponse(responseCode = "500", description = "Erro ao registrar associado")
    })
    @PostMapping    
    public ResponseEntity<String> registerAssociated(@RequestBody AssociatedDTO associatedDTO) throws Exception {
        Associated associated = mappers.associatedDTOToAssociated(associatedDTO);
        associatedService.registerAssociated(associated);
        return ResponseEntity.ok("Associado registrado com sucesso.");
    }


    @Operation(summary = "Buscar associado por CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Associado encontrado"),
        @ApiResponse(responseCode = "404", description = "Associado não encontrado")
    })
    @GetMapping("/cpf")
    public ResponseEntity<AssociatedDTO> getByCpf(@RequestParam String cpf) throws Exception {
        Associated associated = associatedService.findByCpf(cpf);
        AssociatedDTO associatedDto = mappers.associatedToAssociatedDTO(associated);
        return ResponseEntity.ok(associatedDto);
    }

    @Operation(summary = "Listar associados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Associados listados"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<AssociatedDTO>> getAll() {
        List<Associated> list = associatedService.find();
        List<AssociatedDTO> associatedDTOs = mappers.associatedToAssociatedDTO(list);
        return ResponseEntity.ok(associatedDTOs);
    }
    

    @Operation(summary = "Deletar associado por CPF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Associado deletado"),
        @ApiResponse(responseCode = "500", description = "Erro ao deletar associado")
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<AssociatedDTO> deleteById(@PathVariable String cpf) {
        associatedService.deleteAssociated(cpf);
        return ResponseEntity.ok().build();
    }
    
}
