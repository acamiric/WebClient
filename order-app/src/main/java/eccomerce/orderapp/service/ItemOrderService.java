package eccomerce.orderapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eccomerce.itemorder.ItemOrder;
import eccomerce.orderapp.kafka.JsonKafkaProducerOrders;
import eccomerce.orderapp.repository.ItemOrderRepository;

@Service
public class ItemOrderService {

	@Autowired
	private JsonKafkaProducerOrders producer;
	
	@Autowired
	private ItemOrderRepository repository;
	
	@Transactional
	public ItemOrder save(ItemOrder itemOrder) {
	itemOrder.setStatus("CREATED");
	ItemOrder io=	repository.save(itemOrder);
	producer.sendMessage(itemOrder);
	return io;
	}
	
	public List<ItemOrder> getAll(){
		return repository.findAll();
	}
	
	public void delete(ItemOrder itemOrder) {
		repository.delete(itemOrder);
	}
	
	public Optional<ItemOrder> findById(Long id) throws Exception {
		Optional<ItemOrder> io=repository.findById(id);
		if(io.isPresent()) {
			return Optional.of(io.get());
		}
		return Optional.empty();
	}
	
	public ItemOrder update(ItemOrder itemOrder) throws Exception {
		Optional<ItemOrder> existingOrder=repository.findById(itemOrder.getId());
		if(existingOrder.isPresent()) {
			return repository.save(itemOrder);
		}
		throw new Exception("You can not update a non existing item order.");
	}
}
