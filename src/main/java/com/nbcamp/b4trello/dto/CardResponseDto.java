package com.nbcamp.b4trello.dto;

import java.time.LocalDate;

import com.nbcamp.b4trello.entity.Card;

import lombok.Getter;

@Getter
public class CardResponseDto {
	private long id;
	private String title;
	private String content;
	private long columnId;
	private long userId;
	private LocalDate dueDate;

	public CardResponseDto(Card card) {
		this.id = card.getId();
		this.title = card.getTitle();
		this.content = card.getContent();
		columnId = card.getColumn().getId();
		userId = card.getUser().getId();
		dueDate = card.getDueDate();
	}
}
