package com.nbcamp.b4trello.entity;

import java.time.LocalDate;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.nbcamp.b4trello.dto.CardRequestDto;
import com.nbcamp.b4trello.dto.CardUpdateRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY)
	private Column column;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	private String manager;

	private LocalDate dueDate;

	@Builder
	public Card(Column column, CardRequestDto requestDto, User user) {
		this.column = column;
		this.user = user;
		this.manager = requestDto.getManager();
		title = requestDto.getTitle();
		content = requestDto.getContent();
		dueDate = requestDto.getDueDate();
	}

	public void update(CardUpdateRequestDto requestDto) {
		if (requestDto.getTitle() != null) this.title = requestDto.getTitle();
		if (requestDto.getContent() != null) this.content = requestDto.getContent();
		if (requestDto.getDueDate() != null) this.dueDate = requestDto.getDueDate();
		if (requestDto.getManager() != null) this.manager = requestDto.getManager();

	}

	public void move(Column column) {
		this.column = column;
	}
}
