package com.demo.agepublisher.enums;

public enum KafkaConstants {
    CUSTOMER_INPUT("CustomerInputTopic"),
    CUSTOMER_EVEN("CustomerEVEN"),
    CUSTOMER_ODD("CustomerODD"),

    TOPIC_READ_MESSAGES("ReadMessagesFromKafka"),
    TOPIC_PUBLISH_MESSAGES("PublishMessages"),
    TOPIC_PROCESS_MESSAGES("ProcessMessages");

    private final String topicName;

    KafkaConstants(String topicName) {
        this.topicName = topicName;
    }

    public String getValue() {
        return topicName;
    }
}
