package com.demo.agepublisher.functions;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.kafka.common.serialization.StringSerializer;

public class MessagePublishFunction extends DoFn<KV<String, String>, Void> {
    private final String kafkaBootstrapServers;
    private final String evenTopic;
    private final String oddTopic;

    public MessagePublishFunction(String kafkaBootstrapServers, String evenTopic, String oddTopic) {
        this.kafkaBootstrapServers = kafkaBootstrapServers;
        this.evenTopic = evenTopic;
        this.oddTopic = oddTopic;
    }

    @ProcessElement
    public void processElement(ProcessContext c) {
        KV<String, String> kv = c.element();
        String topic = kv.getKey().equals("even") ? evenTopic : oddTopic;

        KafkaIO.<String, String>write()
                .withBootstrapServers(kafkaBootstrapServers)
                .withTopic(topic)
                .withKeySerializer(StringSerializer.class)
                .withValueSerializer(StringSerializer.class);
    }
}
