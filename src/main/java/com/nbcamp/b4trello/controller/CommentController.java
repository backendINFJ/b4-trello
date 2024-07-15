package com.nbcamp.b4trello.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nbcamp.b4trello.dto.CommentRequestDto;
import com.nbcamp.b4trello.dto.CommentResponseDto;
import com.nbcamp.b4trello.dto.CommonResponse;
import com.nbcamp.b4trello.dto.ResponseEnum;
import com.nbcamp.b4trello.security.UserDetailsImpl;
import com.nbcamp.b4trello.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/cards/{card-id}/comments")
@RestController
public class CommentController {

	private final CommentService commentService;

	/**
	 * 댓글 생성
	 * @param cardId
	 * @param requestDto
	 * @param userDetails
	 * @return
	 */
	@PostMapping
	public ResponseEntity<CommonResponse<CommentResponseDto>> createComment(
		@PathVariable("card-id") long cardId, @RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		CommentResponseDto responseDto = commentService.createComment(
			cardId, requestDto.getContent(), userDetails.getUser());

		return ResponseEntity.status(HttpStatus.CREATED).body(
			CommonResponse.<CommentResponseDto>builder()
				.responseEnum(ResponseEnum.CREATE_COMMENT)
				.data(responseDto)
				.build()
		);
	}

	/**
	 * 댓글 조회
	 * @param cardId
	 * @param userDetails
	 * @return
	 */
	@GetMapping
	public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getComments(
		@PathVariable("card-id") long cardId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		List<CommentResponseDto> responseDto = commentService.getComments(
			cardId, userDetails.getUser().getId());

		return ResponseEntity.ok(
			CommonResponse.<List<CommentResponseDto>>builder()
				.responseEnum(ResponseEnum.GET_COMMENT)
				.data(responseDto)
				.build()
		);
	}

}
