package com.norton.desafio_NtConsult.infra.outbound.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.norton.desafio_NtConsult.application.core.domain.Result;
import com.norton.desafio_NtConsult.application.ports.out.IResultQueuePort;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResultProducer implements IResultQueuePort {


    @Value(value = "${rabbitmq.exchange.voting-result}")
    private String exchange;

    @Value(value ="${rabbitmq.routing-key.result}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public ResultProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendResult(Result result) throws JsonProcessingException {
        try {
            String resultString = objectMapper.writeValueAsString(result);
            log.info("Enviando resultado para a fila: {}", resultString);
            rabbitTemplate.convertAndSend(exchange, routingKey, resultString);
        } catch (RuntimeException e) {
            log.error("Erro ao enviar resultado para a fila: {}", e);
            throw new RuntimeException("Operação não realizada, não foi possível enviar para a fila.");
        }
    }
    
}
