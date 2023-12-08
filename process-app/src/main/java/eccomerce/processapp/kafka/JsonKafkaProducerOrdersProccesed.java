package eccomerce.processapp.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import eccomerce.itemorder.ItemOrder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JsonKafkaProducerOrdersProccesed {

	
	private KafkaTemplate<Long, ItemOrder> kafkaTemplate;

	public JsonKafkaProducerOrdersProccesed(KafkaTemplate<Long, ItemOrder> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(ItemOrder data) {
		log.info(String.format("Message sent to orders-processed topic: %s", data.toString()));
		
		Message<ItemOrder> message=
				MessageBuilder
				.withPayload(data)
				.setHeader(KafkaHeaders.TOPIC, "orders-processed")
				.build();
		
		kafkaTemplate.send(message);
	}

}
