package eccomerce.processapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import eccomerce.itemorder.ItemOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

	private final WebClient webClient;

	@Value("${url}")
	public String url;

	public WebClientService(WebClient.Builder webClientBuilder) {
		super();
		this.webClient = webClientBuilder.baseUrl("http://localhost:8081/order").build();
	}

	// uri if it exists - in this case it doesnt
	public Flux<ItemOrder> findAllOrders() {
		return this.webClient.get().retrieve().bodyToFlux(ItemOrder.class);

	}

	public Mono<ItemOrder> findById(Long id) {
		return this.webClient.get().uri("/{id}", id).retrieve().bodyToMono(ItemOrder.class);

	}
}
