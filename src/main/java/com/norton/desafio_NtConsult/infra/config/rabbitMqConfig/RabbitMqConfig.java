package com.norton.desafio_NtConsult.infra.config.rabbitMqConfig;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {


  //routing-key
  @Value(value = "${rabbitmq.routing-key.result}")
  private String routingKey;

  @Value(value = "${rabbitmq.routing-key.result-dlx}")
  private String routingKeyDlx;
  
  @Value(value = "${rabbitmq.routing-key.result-dlx-queue}")
  private String routingKeyDlxQueue;


  //exchange
  @Value(value = "${rabbitmq.exchange.voting-result}")
  private String voutingResult;

  @Value(value = "${rabbitmq.exchange.DLX}")
  private String DLX;


  //queue
  @Value(value = "${rabbitmq.queue.voting-result}")
  private String votingResultQueue;

  @Value(value = "${rabbitmq.queue.DLX}")
  private String deadLetterQueue;

  @Value(value = "${rabbitmq.queue.expired-time}")
  private int expiredTime;



  @Bean
  DirectExchange votingResultExchange() {
    return new DirectExchange(voutingResult, true, false);
  }

  @Bean
  DirectExchange deadLetterExchange() {
    return new DirectExchange(DLX, true, false);
  }

  @Bean
  Queue votingResultQueue() {
    Map<String, Object> argsResult = new HashMap<>();
    argsResult.put("x-dead-letter-exchange", DLX);
    argsResult.put("x-dead-letter-routing-key", routingKeyDlx);
    argsResult.put("x-message-ttl", expiredTime);
    return QueueBuilder.durable(votingResultQueue).withArguments(argsResult).build();
  }

  @Bean
  Queue deadLetterQueue() {
    return QueueBuilder.durable(deadLetterQueue).build();
  }

  @Bean
  Binding votingResultBinding() {
    return BindingBuilder
        .bind(votingResultQueue())
        .to(votingResultExchange())
        .with(routingKey);
  }

  @Bean
  Binding deadLetterBinding() {
    return BindingBuilder
        .bind(votingResultQueue())
        .to(deadLetterExchange())
        .with(routingKeyDlx);
  }

  @Bean
  Binding deadLetterQueueBinding() {
    return BindingBuilder
        .bind(deadLetterQueue())
        .to(deadLetterExchange())
        .with(routingKeyDlxQueue);
  }

}
