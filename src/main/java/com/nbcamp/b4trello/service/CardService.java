package com.nbcamp.b4trello.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nbcamp.b4trello.dto.CardListResponseDto;
import com.nbcamp.b4trello.dto.CardRequestDto;
import com.nbcamp.b4trello.dto.CardResponseDto;
import com.nbcamp.b4trello.dto.CardUpdateRequestDto;
import com.nbcamp.b4trello.dto.ErrorMessageEnum;
import com.nbcamp.b4trello.entity.Card;
import com.nbcamp.b4trello.entity.Columns;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.repository.CardDslRepository;
import com.nbcamp.b4trello.repository.CardRepository;
import com.nbcamp.b4trello.repository.ColumnRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CardService {

	private final CardRepository cardRepository;
	private final CardDslRepository cardDslRepository;
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
		Columns column = columnRepository.findById(columnId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage()));

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
	 *
	 * @param cardId
	 * @param columnId
	 * @param user
	 * @param requestDto
	 * @return
	 */
	@Transactional
	public CardResponseDto updateCard(
		long cardId, long columnId, User user, CardUpdateRequestDto requestDto) {

		if(!columnRepository.existsById(columnId))
			throw new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage());

		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage()));

		if (!card.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(ErrorMessageEnum.MISMATCH_USER.getMessage());
		}

		card.update(requestDto);

		return new CardResponseDto(card);
	}

	/**
	 * 컬럼 위치 바꾸기
	 *
	 * @param moveColumnId
	 * @param cardId
	 * @return
	 */
	@Transactional
	public CardResponseDto moveCard(long moveColumnId, long cardId) {
		Columns column = columnRepository.findById(moveColumnId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage()));

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
		if(!columnRepository.existsById(columnId))
			throw new IllegalArgumentException(ErrorMessageEnum.COLUMN_NOT_FOUND.getMessage());

		Card card = cardRepository.findById(cardId).orElseThrow(
			() -> new IllegalArgumentException(ErrorMessageEnum.CARD_NOT_FOUND.getMessage()));

		if (!card.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(ErrorMessageEnum.MISMATCH_USER.getMessage());
		}
		cardRepository.delete(card);
	}

	public CardListResponseDto getCardList(String sortBy, long boardId) {

		if (!(sortBy.equals("column") || sortBy.equals("id") || sortBy.equals("manager"))) {
			throw new IllegalArgumentException(ErrorMessageEnum.BAD_PARAM.getMessage());
		}

		Sort sort = Sort.by(Sort.Direction.DESC, sortBy);

		return new CardListResponseDto(
			cardDslRepository.getCards(sort, boardId)
				.stream()
				.map(CardResponseDto::new)
				.toList());
	}
}
