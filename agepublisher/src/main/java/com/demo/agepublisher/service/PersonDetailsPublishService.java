package com.demo.agepublisher.service;

import com.demo.agepublisher.enums.KafkaConstants;
import com.demo.agepublisher.model.Person;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsPublishService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public PersonDetailsPublishService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPersonDataToKafka(Person person) {
        String message = person.getFirstName() + "," + person.getLastName() + "," + person.getDateOfBirth();
        kafkaTemplate.send(KafkaConstants.CUSTOMER_INPUT.getValue(), message);
    }
}
