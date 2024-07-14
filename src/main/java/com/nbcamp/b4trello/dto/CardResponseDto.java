package com.nbcamp.b4trello.dto;

import java.time.LocalDate;

import com.nbcamp.b4trello.entity.Card;

import lombok.Getter;

@Getter
public class CardResponseDto {
	private final long id;
	private final long userId;
	private final long columnId;
	private final String title;
	private final String content;
	private final String manager;
	private final LocalDate dueDate;

	public CardResponseDto(Card card) {
		this.id = card.getId();
		userId = card.getUser().getId();
		columnId = card.getColumn().getColumnId();
		this.title = card.getTitle();
		this.content = card.getContent();
		manager = card.getManager();
		dueDate = card.getDueDate();
	}
}
