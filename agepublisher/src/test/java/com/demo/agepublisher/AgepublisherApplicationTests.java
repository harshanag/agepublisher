package com.demo.agepublisher;

import com.demo.agepublisher.enums.KafkaConstants;
import com.demo.agepublisher.model.Person;
import com.demo.agepublisher.service.PersonDetailsPublishService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AgepublisherApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;

	@InjectMocks
	private PersonDetailsPublishService personDetailsPublishService;

	public AgepublisherApplicationTests() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testConsumeMessageEvenAge() {
		Person person = new Person("John", "Doe", "1990-01-01");
		personDetailsPublishService.sendPersonDataToKafka(person);

		verify(kafkaTemplate, times(1)).send(eq(KafkaConstants.CUSTOMER_EVEN.getValue()), anyString());
	}

	@Test
	public void testConsumeMessageOddAge() {
		Person person = new Person("John", "Doe", "1990-01-01");
		personDetailsPublishService.sendPersonDataToKafka(person);

		verify(kafkaTemplate, times(1)).send(eq(KafkaConstants.CUSTOMER_ODD.getValue()), anyString());
	}

}
