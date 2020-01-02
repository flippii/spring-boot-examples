package de.springframework.monitoring.resilience4j.consumer.web.mvc;

import de.springframework.monitoring.resilience4j.consumer.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("rest")
public class PersonMvcController {

    private final PersonService personService;

    @GetMapping
    public String index(Model model) {
        log.info("MVC request to show all Persons.");
        model.addAttribute("path", "rest");
        model.addAttribute("persons", personService.getPersons());
        return "index";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable Long id) {
        log.info("MVC request to show Person: {}.", id);
        model.addAttribute("person", personService.getPersonById(id));
        return "details";
    }

}
