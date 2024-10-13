package com.demo.agepublisher.controller;

import com.demo.agepublisher.constants.ApplicationEndpoints;
import com.demo.agepublisher.model.Person;
import com.demo.agepublisher.service.PersonDetailsPublishService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApplicationEndpoints.API_ENDPOINT+ApplicationEndpoints.API_VERSION_V1)
public class UserController {

    private final PersonDetailsPublishService personService;

    public UserController(PersonDetailsPublishService personDetailsPublishService) {
        this.personService = personDetailsPublishService;
    }

    @PostMapping(ApplicationEndpoints.API_SEND_USER_DETAILS_ENDPOINT)
    public Person sendMessage(@RequestBody Person person) {
        personService.sendPersonDataToKafka(person);
        return person;
    }
}
