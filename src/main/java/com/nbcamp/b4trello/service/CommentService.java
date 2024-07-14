package com.nbcamp.b4trello.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbcamp.b4trello.dto.CommentResponseDto;
import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.entity.Card;
import com.nbcamp.b4trello.entity.Comment;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.repository.CardRepository;
import com.nbcamp.b4trello.repository.CommentRepository;
import com.nbcamp.b4trello.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final CardRepository cardRepository;
	private final UserRepository userRepository;

	@Transactional
	public CommentResponseDto createComment(long cardId, String content, User user) {
		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage()));

		Comment comment = Comment.builder()
			.content(content)
			.user(user)
			.card(card)
			.build();
		commentRepository.save(comment);

		return new CommentResponseDto(comment);
	}

}
