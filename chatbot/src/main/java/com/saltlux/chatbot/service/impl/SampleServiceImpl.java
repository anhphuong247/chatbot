package com.saltlux.chatbot.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.saltlux.chatbot.model.Item;
import com.saltlux.chatbot.service.SampleService;

@Service
public class SampleServiceImpl implements SampleService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Item> items;

	static {
		items = populateDummyItems();
	}

	@Override
	public List<Item> findAllItems() {
		return items;
	}

	@Override
	public Item findById(long id) {
		for (Item item : items) {
			if (item.getId() == id) {
				return item;
			}
		}
		return null;
	}

	@Override
	public Item findByName(String name) {
		for (Item item : items) {
			if (item.getName().equalsIgnoreCase(name)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public void saveItem(Item item) {
		item.setId(counter.incrementAndGet());
		items.add(item);
	}

	@Override
	public void updateItem(Item item) {
		int index = items.indexOf(item);
		items.set(index, item);
	}

	@Override
	public void deleteItemById(long id) {
		for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
			Item item = iterator.next();
			if (item.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public boolean isItemExist(Item item) {
		return findByName(item.getName()) != null;
	}

	@Override
	public void deleteAllItems() {
		items.clear();
	}

	private static List<Item> populateDummyItems() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(counter.incrementAndGet(), "Table", 70000, "(-.-)"));
		items.add(new Item(counter.incrementAndGet(), "Chair", 50000, "(-_-)"));
		items.add(new Item(counter.incrementAndGet(), "Keyboard", 30000, "(^.^)"));
		items.add(new Item(counter.incrementAndGet(), "Mouse", 40000, "(@.@)"));
		return items;
	}

}
