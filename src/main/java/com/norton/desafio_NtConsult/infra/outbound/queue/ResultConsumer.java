package com.norton.desafio_NtConsult.infra.outbound.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.norton.desafio_NtConsult.infra.config.exceptions.ForbiddenException;

@Component
public class ResultConsumer implements MessageListener {

    @Override
    @RabbitListener(queues = "${rabbitmq.queue.voting-result}")
    public void onMessage(Message message) {
        try {
            System.out.println("Mensagem recebida: " + new String(message.getBody()));
        } catch (Exception e) {
            throw new ForbiddenException("Erro ao receber mensagem da fila.");
        }

    }
    
}
