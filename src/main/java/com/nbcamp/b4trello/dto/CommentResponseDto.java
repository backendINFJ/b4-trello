package com.nbcamp.b4trello.dto;

import com.nbcamp.b4trello.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
	private final long id;
	private final long userId;
	private final long cardId;
	private final String content;

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.userId = comment.getUser().getId();
		this.cardId = comment.getCard().getId();
		this.content = comment.getContent();
	}
}