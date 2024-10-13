package com.demo.agepublisher.enums;

public enum KafkaConstants {
    CUSTOMER_INPUT("CustomerInputTopic"),
    CUSTOMER_EVEN("CustomerEVEN"),
    CUSTOMER_ODD("CustomerODD"),

    TOPIC_READ_MESSAGES("ReadMessagesFromKafka"),
    TOPIC_PUBLISH_MESSAGES("PublishMessages"),
    TOPIC_PROCESS_MESSAGES("ProcessMessages"),

    KEY_PREFIX_EVEN("even"),
    KEY_PREFIX_ODD("odd");

    private final String name;

    KafkaConstants(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
