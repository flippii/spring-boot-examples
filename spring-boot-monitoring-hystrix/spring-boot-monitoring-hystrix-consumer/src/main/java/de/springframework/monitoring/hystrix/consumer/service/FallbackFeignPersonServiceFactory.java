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
    public FeignPersonService create(Throwable th) {
        if (th instanceof FeignException && ((FeignException) th).status() == 404) {
            log.error("A FeignException occurred: {}.", th.getMessage(), th);
        } else {
            log.info("Feign exception in fallback factory ({}).", th.getMessage());
        }

        return new FallbackFeignPersonService();
    }

    private static class FallbackFeignPersonService implements FeignPersonService {

        @Override
        public List<PersonDto> getPersons() {
            log.info("Fallback: get empty persons list.");
            return Collections.singletonList(getAnonymousPerson());
        }

        @Override
        public PersonDto getPersonById(Long id) {
            log.info("Fallback: get ANONYMOUS person with id: {}.", id);
            return getAnonymousPerson();
        }

        private PersonDto getAnonymousPerson() {
            return PersonDto.builder()
                    .id(0L)
                    .firstName("ANONYMOUS")
                    .lastName("no")
                    .build();
        }

    }

}
