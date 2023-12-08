package eccomerce.orderapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

	//creating the neccessery topics if they dont already exist

			@Bean
			public NewTopic javaguidesTopic() {
				return TopicBuilder.name("orders").build();
			}
			
			@Bean
			public NewTopic javaguidesJsonTopic() {
				return TopicBuilder.name("orders-processed").build();
			}
}
