package com.nbcamp.b4trello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nbcamp.b4trello.dto.CardRequestDto;
import com.nbcamp.b4trello.dto.CardResponseDto;
import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.CardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/column/{columnId}/cards")
@RestController
public class CardController {

	private final CardService cardService;

	/**
	 *카드 생성 컨트롤
	 * @param columnId 경로값으로 받음, 카드에 필요한 필더값
	 * @param cardRequestDto 카드에 필요한 필더값
	 * @param userDetails 해당 유저가 수정 권한이 주어진다.
	 * @return 카드정보를 반환
	 */
	@PostMapping
	public ResponseEntity<CommonResponse<CardResponseDto>> createCard(
		@PathVariable("columnId") long columnId,
		@RequestBody CardRequestDto cardRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		CardResponseDto responseDto = cardService.createCard(
			columnId, cardRequestDto, userDetails.getUser());
		return ResponseEntity.ok(
			new CommonResponse<CardResponseDto>(
				ResponseEnum.CREATE_CARD, responseDto));
	}

	/**
	 * 카드 단권 조회
	 * @param cardId 조회할 카드 아아디
	 * @return CardResponseDto 반환
	 */
	@GetMapping("/{cardId}")
	public ResponseEntity<CommonResponse<CardResponseDto>> getCard(
		@RequestParam long cardId) {

		CardResponseDto responseDto = cardService.getCard(cardId);
		return ResponseEntity.ok(
			new CommonResponse<CardResponseDto>(
				ResponseEnum.GET_CARD, responseDto));
	}
}
