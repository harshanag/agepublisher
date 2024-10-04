package com.demo.agepublisher.functions;

import com.demo.agepublisher.util.AgeUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AgeProcessingFunction extends DoFn<KV<String, String>, KV<String, String>> {
    @ProcessElement
    public void processElement(ProcessContext c) {
        String message = c.element().getValue();
        String[] parts = message.split(",");
        String dob = parts[2];

        int age = calculateAge(dob);
        if (AgeUtils.isEven(age)) {
            c.output(KV.of("even", message));
        } else {
            c.output(KV.of("odd", message));
        }
    }

    private int calculateAge(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dob, formatter);
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}

