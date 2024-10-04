package com.demo.agepublisher;

import com.demo.agepublisher.enums.KafkaTopics;
import com.demo.agepublisher.functions.AgeProcessingFunction;
import com.demo.agepublisher.functions.MessagePublishFunction;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.transforms.ParDo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;

@SpringBootApplication
public class AgepublisherApplication {

	public static void main(String[] args) {
		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(org.apache.beam.runners.flink.FlinkRunner.class);
		Pipeline pipeline = Pipeline.create(options);

		String kafkaBootstrapServers = "localhost:9092";
		String inputTopic = KafkaTopics.CUSTOMER_INPUT.getTopicName();
		String evenTopic = KafkaTopics.CUSTOMER_EVEN.getTopicName();
		String oddTopic = KafkaTopics.CUSTOMER_ODD.getTopicName();

		pipeline
				.apply("ReadMessagesFromKafka",
						KafkaIO.<String, String>read()
								.withBootstrapServers(kafkaBootstrapServers)
								.withTopic(inputTopic)
								.withKeyDeserializer(StringDeserializer.class)
								.withValueDeserializer(StringDeserializer.class)
								.withoutMetadata())
				.apply("ProcessMessages", ParDo.of(new AgeProcessingFunction()))
				.apply("PublishMessages", ParDo.of(new MessagePublishFunction(kafkaBootstrapServers, evenTopic, oddTopic)));

		pipeline.run().waitUntilFinish();
	}

}
