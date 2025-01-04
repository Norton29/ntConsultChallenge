package com.norton.desafio_NtConsult.infra.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.norton.desafio_NtConsult.application.core.usecase.AgendaService;
import com.norton.desafio_NtConsult.application.core.usecase.AssociatedService;
import com.norton.desafio_NtConsult.application.core.usecase.PollService;
import com.norton.desafio_NtConsult.application.ports.out.IAgendaRepositoryPort;
import com.norton.desafio_NtConsult.application.ports.out.IAssociatedRepositoryPort;
import com.norton.desafio_NtConsult.application.ports.out.ICPFValidatorPort;
import com.norton.desafio_NtConsult.application.ports.out.IPollRepositoryPort;
import com.norton.desafio_NtConsult.infra.config.exceptions.GlobalExceptionHandler;

@Configuration
public class Config {

    @Bean
    AgendaService agendaService(IAgendaRepositoryPort agendaRepository) {
        return new AgendaService(agendaRepository);
    }

    @Bean
    AssociatedService associatedService(IAssociatedRepositoryPort associatedRepository) {
        return new AssociatedService(associatedRepository);
    }

    @Bean
    PollService pollService(IPollRepositoryPort pollRepository, AgendaService agendaService,
            AssociatedService associatedService, ICPFValidatorPort cpfValidator) {
        return new PollService(pollRepository, agendaService, associatedService, cpfValidator);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
