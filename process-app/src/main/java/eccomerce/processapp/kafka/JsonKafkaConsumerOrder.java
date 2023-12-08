package eccomerce.processapp.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import eccomerce.itemorder.ItemOrder;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class JsonKafkaConsumerOrder {
		
	@Autowired
	private JsonKafkaProducerOrdersProccesed producer;
	

	@KafkaListener(topics = "orders", groupId = "myGroup")
	public void consume(ItemOrder order) throws InterruptedException {
		//log that the message is recieved to orders topic
		log.info(String.format("Json message recieved  on orders topic :%s ", order.toString()));
		
		Thread.sleep(5000);
		//if the credit card number is even set the order status to accepted
		if(order.getCreditCardNo()%2==0) {
			order.setStatus("ACCEPTED");
			log.info("Status of order: " +order.getId() +" changed to accepted.");
			}
		//if its not set it to denied
		else {
			order.setStatus("DENIED");
			log.info("Status of order: " +order.getId() +" changed to denied.");
		}
		//send the finsihed order to the orders-proccesed topic
		producer.sendMessage(order);
		
	}	
	
}
