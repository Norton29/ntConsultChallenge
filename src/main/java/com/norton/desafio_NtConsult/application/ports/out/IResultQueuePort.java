package com.norton.desafio_NtConsult.application.ports.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.norton.desafio_NtConsult.application.core.domain.Result;

public interface IResultQueuePort {

    public void sendResult(Result result) throws JsonProcessingException;

}
