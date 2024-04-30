package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	Logger logger = LoggerFactory.getLogger(ItemRepository.class.getSimpleName());

	@Autowired
	private ItemRepository itemRepository;


	private static final String ITEM_NOT_FOUND_BY_ID_ERROR = "Could not find Item with provided ID: ";
	private static final String ITEM_NOT_FOUND_BY_NAME_ERROR = "Could not find Item with provided Name: ";

	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {

		Optional<Item> item = itemRepository.findById(id);

		if (!item.isPresent()){
			logger.warn(ITEM_NOT_FOUND_BY_ID_ERROR+id);
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.of(item);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		List<Item> items = itemRepository.findByName(name);
		if(items == null || items.isEmpty()){
			logger.warn(ITEM_NOT_FOUND_BY_NAME_ERROR);
		}
		return items == null || items.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(items);
	}
}
