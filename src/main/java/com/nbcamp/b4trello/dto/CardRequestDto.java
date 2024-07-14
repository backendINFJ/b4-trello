package com.nbcamp.b4trello.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CardRequestDto {
	@NotEmpty
	private String title;
	private String content;
	private long userId;
	private String manager;
	private LocalDate dueDate;
}
