package com.nbcamp.b4trello.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CardListResponseDto {
	List<CardResponseDto> cards;
}
