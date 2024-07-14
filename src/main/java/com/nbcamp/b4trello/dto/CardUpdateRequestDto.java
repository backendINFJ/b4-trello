package com.nbcamp.b4trello.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class CardUpdateRequestDto {
	private String title;
	private String content;
	private LocalDate dueDate;
	private String manager;
}