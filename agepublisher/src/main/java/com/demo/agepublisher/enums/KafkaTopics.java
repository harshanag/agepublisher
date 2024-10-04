package com.demo.agepublisher.enums;

public enum KafkaTopics {
    CUSTOMER_INPUT("CustomerInputTopic"),
    CUSTOMER_EVEN("CustomerEVEN"),
    CUSTOMER_ODD("CustomerODD");

    private final String topicName;

    KafkaTopics(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
