package de.springframework.monitoring.resilience4j.consumer.web.mvc;

import de.springframework.monitoring.resilience4j.consumer.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("rest")
public class PersonRestController {

    private final PersonService personService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("path", "rest");
        model.addAttribute("persons", personService.getPersons());
        return "index";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) {
        model.addAttribute("person", personService.getPersonById(id));
        return "details";
    }

}
