package de.springframework.monitoring.hystrix.consumer.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping
    public String index() {
        return "redirect:/rest";
    }

}
