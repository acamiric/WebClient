package eccomerce.orderapp.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import eccomerce.itemorder.ItemOrder;
import eccomerce.orderapp.service.ItemOrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class ItemOrderRestController {

	@Autowired
	private ItemOrderService service;

	@PostMapping
	@ApiOperation(
			value = "Creates a new Item Order",
			notes = "Creates the Item Order sends to the Kafka topic and inserts it in the database.",
			response = ItemOrder.class)
	public ResponseEntity<String> post(@RequestBody ItemOrder itemOrder) {
		service.save(itemOrder);
		log.info("An order has been placed, sent to topic orders , and added to the Database with id: "
				+ itemOrder.getId());
		return ResponseEntity
				.ok("Json Message sent to the topic and Order saved to the Database with id: " + itemOrder.getId());
	}

	@GetMapping()
	@ApiOperation(
			value = "Find all Item Orders",
			notes = "This method returns all the orders in the system.",
			response = ItemOrder.class)
	public List<ItemOrder> findAll() {
		return service.getAll();
	}

	@DeleteMapping("{id}")
	@ApiOperation(
			value = "Delete an Item Order by their ID",
			notes = "Provide an Id to delete an Item Order if it exists in the system.",
			response = ItemOrder.class)
	public @ResponseBody ResponseEntity<String> delete(@ApiParam(value = "ID value for the order you want to delete. The ID is a number.",required = true)
	@PathVariable String id)  {
		try {
			Optional<ItemOrder> itemOrder = service.findById(Long.parseLong(id));

			if (itemOrder.isEmpty()) {
				throw new Exception("Can not delete non existing item order!");
			}

			service.delete(itemOrder.get());

			return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted Order with id: " + id);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("{id}")
	@ApiOperation(
			value = "Find Item Orders by their ID",
			notes = "Provide an Id to check if the order with that ID exists in the system.",
			response = ItemOrder.class)
	public @ResponseBody ResponseEntity<Object> findById(
			@ApiParam(value = "ID value for the order you want to search. The ID is a number.",required = true)
			@PathVariable String id) throws Exception {
		Optional<ItemOrder> itemOrder = service.findById(Long.parseLong(id));
		if (itemOrder.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(itemOrder.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("Invalid search - Order with id: " + id + " does not exist! ");
	}

	@PutMapping()
	@ApiOperation(
			value = "Updates the Item Order",
			notes = "Updates the Item Order if it exists in the system.",
			response = ItemOrder.class)
	public @ResponseBody ResponseEntity<Object> update(@RequestBody ItemOrder itemOrder) throws Exception {

		try {
			ItemOrder entity = service.update(itemOrder);
			return ResponseEntity.status(HttpStatus.OK).body(entity);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

}