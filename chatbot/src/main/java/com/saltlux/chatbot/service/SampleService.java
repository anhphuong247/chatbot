package com.saltlux.chatbot.service;

import java.util.List;

import com.saltlux.chatbot.model.Item;

public interface SampleService {
	Item findById(long id);

	Item findByName(String name);

	void saveItem(Item item);

	void updateItem(Item item);

	void deleteItemById(long id);

	List<Item> findAllItems();

	void deleteAllItems();

	boolean isItemExist(Item item);
}
