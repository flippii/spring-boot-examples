package de.springframework.monitoring.hystrix.consumer.service;

import de.springframework.monitoring.hystrix.consumer.configuration.FeignConfiguration;
import de.springframework.monitoring.hystrix.consumer.service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "${producer.service.host}",
        path = "${producer.service.path}/persons",
        fallbackFactory = FallbackFeignPersonServiceFactory.class,
        configuration = FeignConfiguration.class
)
public interface FeignPersonService {

    @GetMapping
    List<PersonDto> getPersons();

    @GetMapping("/{id}")
    PersonDto getPersonById(@PathVariable("id") Long id);

}
