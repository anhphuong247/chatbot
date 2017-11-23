package com.saltlux.chatbot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.saltlux.chatbot.model.Item;
import com.saltlux.chatbot.service.SampleService;
import com.saltlux.chatbot.util.ApiError;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class SampleController {
	@Autowired
	SampleService sampleService;

	// -------------------Retrieve All
	// Items---------------------------------------------
	@GetMapping(value = "/item")
	public ResponseEntity<List<Item>> listItems() {
		List<Item> items = sampleService.findAllItems();
		if (items.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Item------------------------------------------
	@GetMapping(value = "/item/{id}")
	public ResponseEntity<?> getItem(@PathVariable("id") long id) {
		log.info("Fetching Item with id {}", id);
		Item item = sampleService.findById(id);
		if (item == null) {
			log.error("Item with id {} not found.", id);
			return new ResponseEntity(new ApiError("Item with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	// -------------------Create a Item-------------------------------------------
	@PostMapping(value = "/item")
	public ResponseEntity<?> createItem(@RequestBody Item item, UriComponentsBuilder ucBuilder) {
		log.info("Creating Item : {}", item);

		if (sampleService.isItemExist(item)) {
			log.error("Unable to create. A Item with name {} already exist", item.getName());
			return new ResponseEntity(
					new ApiError("Unable to create. A Item with name " + item.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		sampleService.saveItem(item);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/item/{id}").buildAndExpand(item.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Item
	// ------------------------------------------------
	@PutMapping(value = "/item/{id}")
	public ResponseEntity<?> updateItem(@PathVariable("id") long id, @RequestBody Item item) {
		log.info("Updating Item with id {}", id);

		Item currentItem = sampleService.findById(id);

		if (currentItem == null) {
			log.error("Unable to update. Item with id {} not found.", id);
			return new ResponseEntity(new ApiError("Unable to upate. Item with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentItem.setName(item.getName());
		currentItem.setPrice(item.getPrice());
		currentItem.setDescription(item.getDescription());

		sampleService.updateItem(currentItem);
		return new ResponseEntity<Item>(currentItem, HttpStatus.OK);
	}

	// ------------------- Delete a Item-----------------------------------------
	@DeleteMapping(value = "/item/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable("id") long id) {
		log.info("Fetching & Deleting Item with id {}", id);

		Item item = sampleService.findById(id);
		if (item == null) {
			log.error("Unable to delete. Item with id {} not found.", id);
			return new ResponseEntity(new ApiError("Unable to delete. Item with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		sampleService.deleteItemById(id);
		return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Items-----------------------------
	@DeleteMapping(value = "/item/")
	public ResponseEntity<Item> deleteAllItems() {
		log.info("Deleting All Items");

		sampleService.deleteAllItems();
		return new ResponseEntity<Item>(HttpStatus.NO_CONTENT);
	}
}
