package com.demo.agepublisher.controller;

import com.demo.agepublisher.model.Person;
import com.demo.agepublisher.service.PersonDetailsPublishService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PersonController {

    private final PersonDetailsPublishService personService;

    public PersonController(PersonDetailsPublishService personDetailsPublishService) {
        this.personService = personDetailsPublishService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody Person person) {
        personService.sendPersonDataToKafka(person);
        return "Message sent to Kafka for person: " + person;
    }
}
