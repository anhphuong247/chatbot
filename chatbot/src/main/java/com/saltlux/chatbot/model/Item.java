package com.saltlux.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Item {
	private long id;
	private String name;
	private long price;
	private String description;
}
