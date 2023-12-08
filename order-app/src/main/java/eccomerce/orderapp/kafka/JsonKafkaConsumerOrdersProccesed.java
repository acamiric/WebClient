package eccomerce.orderapp.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import eccomerce.itemorder.ItemOrder;
import eccomerce.orderapp.repository.ItemOrderRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JsonKafkaConsumerOrdersProccesed {
	@Autowired
	private ItemOrderRepository itemOrderRepository;
		
	@KafkaListener(topics = "orders-processed", groupId = "myGroup")
	public void consume(ItemOrder order) {
		log.info(String.format("Json message recieved to orders-processed topic: %s ", order.toString()));
		log.info(String.format("Database updated the entry: %s ", order.toString()));
		itemOrderRepository.save(order);

	}
}