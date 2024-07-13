package com.nbcamp.b4trello.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbcamp.b4trello.dto.CardResponseDto;
import com.nbcamp.b4trello.dto.CardUpdateRequestDto;
import com.nbcamp.b4trello.dto.ColumnRepository;
import com.nbcamp.b4trello.dto.CardRequestDto;
import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.entity.Card;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.repository.CardRepository;
import com.nbcamp.b4trello.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CardService {

	private final CardRepository cardRepository;
	private final ColumnRepository columnRepository;

	/**
	 * 카드 생성 후 저장
	 * @param columnId 컬럼이 있는지 확인하고 주입
	 * @param requestDto 카드에 필요한 필드를 받아온다.
	 * @param user 유저도 주입
	 * @return 저장한 카드 디티오로 반환
	 */
	@Transactional
	public CardResponseDto createCard(long columnId, CardRequestDto requestDto, User user) {
		Column column = columnRepository.findById(columnId).orElseThrow();

		Card card = cardRepository.save( Card.builder()
			.column(column)
			.requestDto(requestDto)
			.user(user)
			.build());

		return new CardResponseDto(card);
	}

	/**
	 * 카드 단권 조회
	 * @param cardId 아이디 조회
	 * @return CardResponseDto 로 반환
	 */
	public CardResponseDto getCard(long cardId) {
		return new CardResponseDto(cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage())));
	}

	/**
	 * 카드 내용 바꾸기 (컬럼 제외)
	 * @param cardId
	 * @param user
	 * @param requestDto
	 * @return
	 */
	@Transactional
	public CardResponseDto updateCard(
		long cardId, User user, CardUpdateRequestDto requestDto) {

		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage()));

		if (!card.getUser().equals(user)) {
			throw new IllegalArgumentException(ErrorMessageEnum.MISMATCH_USER.getMessage());
		}

		card.update(column, requestDto);

		return new CardResponseDto(card);
	}

	/**
	 * 컬럼 위치 바꾸기
	 * @param columnId
	 * @param cardId
	 * @return
	 */
	@Transactional
	public CardResponseDto moveCard(long columnId, long cardId) {
		Column column = columnRepository.findById(columnId).orElseThrow();

		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage()));

		card.move(column);

		return new CardResponseDto(card);
	}

	/**
	 * 카드 삭제
	 * @param cardId
	 * @param columnId
	 * @param user
	 */
	@Transactional
	public void deleteCard(long cardId, long columnId, User user) {
		Column column = columnRepository.findById(columnId).orElseThrow();

		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage()));

		if (!card.getUser().equals(user)) {
			throw new IllegalArgumentException(ErrorMessageEnum.MISMATCH_USER.getMessage());
		}
		cardRepository.delete(card);
	}
}
