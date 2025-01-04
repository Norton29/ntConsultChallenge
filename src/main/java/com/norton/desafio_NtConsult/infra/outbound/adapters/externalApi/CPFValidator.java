package com.norton.desafio_NtConsult.infra.outbound.adapters.externalApi;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.norton.desafio_NtConsult.application.ports.out.ICPFValidatorPort;

@Component
public class CPFValidator implements ICPFValidatorPort {

    private final WebClient webClient;

    public CPFValidator(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://user-info.herokuapp.com").build();
    }

    @Override
    public boolean isValid(String cpf) {
        return webClient.get()
        .uri("/users/{cpf}", cpf)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
            return clientResponse.bodyToMono(String.class)
            .map(error -> new RuntimeException("API Error: " + error));
        })
        .bodyToMono(String.class)
        .block()
        .contains("ABLE_TO_VOTE");
    }

}
