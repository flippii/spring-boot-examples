package de.springframework.monitoring.hystrix.consumer.web.mvc;

import de.springframework.monitoring.hystrix.consumer.service.RestTemplatePersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PersonRestController {

    private final RestTemplatePersonService personService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("persons", personService.getPersons());
        return "index";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("person", personService.getPersonById(id));
        return "details";
    }

}
