package de.springframework.monitoring.hystrix.consumer.service;

import de.springframework.monitoring.hystrix.consumer.service.dto.PersonDto;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class FallbackFeignPersonServiceFactory implements FallbackFactory<FeignPersonService> {

    @Override
    public FeignPersonService create(Throwable cause) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error("A FeignException occurred: {}.", cause.getMessage(), cause);
        }
        return new FallbackFeignPersonService();
    }

    private static class FallbackFeignPersonService implements FeignPersonService {

        @Override
        public List<PersonDto> getPersons() {
            log.info("Fallback: get empty persons list.");
            return Collections.emptyList();
        }

        @Override
        public PersonDto getPersonById(Long id) {
            log.warn("Fallback: get ANONYMOUS person with id: {}.", id);

            return PersonDto.builder()
                    .id(id)
                    .firstName("ANONYMOUS")
                    .build();
        }

    }

}
