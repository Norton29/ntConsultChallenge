package com.norton.desafio_NtConsult.application.ports.in;

import java.util.List;

import com.norton.desafio_NtConsult.application.core.domain.Associated;

public interface IAssociatedServicePort {

    public void registerAssociated(Associated associated) throws Exception;

    public Associated findByCpf(String cpf);

    public void deleteAssociated(String cpf);

    public List<Associated> find();
    
}
