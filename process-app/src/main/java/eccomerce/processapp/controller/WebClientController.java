package eccomerce.processapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eccomerce.itemorder.ItemOrder;
import eccomerce.processapp.services.WebClientService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/web")
public class WebClientController {

	@Autowired
	private WebClientService webClientService;
	
	@GetMapping
	public Flux<ItemOrder> findAllOrders(){
		return webClientService.findAllOrders();
	}
	
	@GetMapping("/{id}")
	public Mono<ItemOrder> findById(@PathVariable Long id){
		return webClientService.findById(id);
	}
}
