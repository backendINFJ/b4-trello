package com.nbcamp.b4trello.service;

import org.springframework.stereotype.Service;

import com.nbcamp.b4trello.dto.CardResponseDto;
import com.nbcamp.b4trello.dto.ColumnRepository;
import com.nbcamp.b4trello.dto.CardRequestDto;
import com.nbcamp.b4trello.entity.Card;
import com.nbcamp.b4trello.entity.User;
import com.nbcamp.b4trello.repository.CardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
	public CardResponseDto createCard(long columnId, CardRequestDto requestDto, User user) {
		Column column = columnRepository.findById(columnId).orElseThrow();
		Card card = cardRepository.save(new Card(column, requestDto, user));

		return new CardResponseDto(card);
	}
}
