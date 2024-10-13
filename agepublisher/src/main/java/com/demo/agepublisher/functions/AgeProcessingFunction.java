package com.demo.agepublisher.functions;

import com.demo.agepublisher.enums.KafkaConstants;
import com.demo.agepublisher.util.AgeUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeProcessingFunction extends DoFn<KV<String, String>, KV<String, String>> {

    private static Logger logger = LoggerFactory.getLogger(AgeProcessingFunction.class);

    @ProcessElement
    public void processElement(ProcessContext c) {
        String message = c.element().getValue();
        String[] parts = message.split(",");
        String dob = parts[2];

        int age = calculateAge(dob);
        if (AgeUtils.isEven(age)) {
            c.output(KV.of(KafkaConstants.KEY_PREFIX_EVEN.getValue(), message));
        } else {
            c.output(KV.of(KafkaConstants.KEY_PREFIX_ODD.getValue(), message));
        }
    }

    private int calculateAge(String dob) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(KafkaConstants.AGE_CALCULATION_DATE_FORMAT.getValue());
            LocalDate birthDate = LocalDate.parse(dob, formatter);
            return Period.between(birthDate, LocalDate.now()).getYears();
        } catch (DateTimeException e) {
            logger.error("Error parsing date of birth :", e);
            return 1;
        }
    }
}

