package com.demo.agepublisher;

import com.demo.agepublisher.enums.KafkaConstants;
import com.demo.agepublisher.functions.AgeProcessingFunction;
import com.demo.agepublisher.functions.MessagePublishFunction;
import org.apache.beam.runners.direct.DirectRunner;
import org.apache.beam.sdk.io.kafka.KafkaIO;
import org.apache.beam.sdk.options.*;
import org.apache.beam.sdk.transforms.ParDo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.apache.beam.sdk.Pipeline;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
public class AgepublisherApplication implements ApplicationListener<ContextRefreshedEvent> {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaBootstrapServer;

	public static void main(String[] args) {
		SpringApplication.run(AgepublisherApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		runBeamPipelineLine();
	}

	private void runBeamPipelineLine() {
		PipelineOptions options = PipelineOptionsFactory.create();
		options.setRunner(DirectRunner.class);
		Pipeline pipeline = Pipeline.create(options);

		String inputTopic = KafkaConstants.CUSTOMER_INPUT.getValue();
		String evenTopic = KafkaConstants.CUSTOMER_EVEN.getValue();
		String oddTopic = KafkaConstants.CUSTOMER_ODD.getValue();

		pipeline
				.apply(KafkaConstants.TOPIC_READ_MESSAGES.getValue(),
						KafkaIO.<String, String>read()
								.withBootstrapServers(kafkaBootstrapServer)
								.withTopic(inputTopic)
								.withKeyDeserializer(StringDeserializer.class)
								.withValueDeserializer(StringDeserializer.class)
								.withoutMetadata())
				.apply(KafkaConstants.TOPIC_PROCESS_MESSAGES.getValue(), ParDo.of(new AgeProcessingFunction()))
				.apply(KafkaConstants.TOPIC_PUBLISH_MESSAGES.getValue(), ParDo.of(new MessagePublishFunction(kafkaBootstrapServer, evenTopic, oddTopic)));

		pipeline.run().waitUntilFinish();
	}

}
