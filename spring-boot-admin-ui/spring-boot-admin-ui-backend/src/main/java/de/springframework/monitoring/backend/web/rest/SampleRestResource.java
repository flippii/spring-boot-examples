package de.springframework.monitoring.backend.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleRestResource {

    @GetMapping("/")
    public String sayHello() {
        return "Hello World!!";
    }

}
