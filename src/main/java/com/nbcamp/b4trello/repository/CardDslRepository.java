package com.nbcamp.b4trello.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.nbcamp.b4trello.entity.Card;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CardDslRepository {

	private final JPAQueryFactory jpaQueryFactory;

	QCard card = QCard.card;
	QBoard board = QBoard.borad;
	QColumn column = QColumn.column;

	public List<Card> getCards(Sort sort, long boardId) {

		// 정렬 조건을 가져옴
		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sort, card);

		// OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, post.createdAt);

		return jpaQueryFactory.select(card)
			.from(card)
			.join(card.column, column)
			.fetchJoin()
			.where(card.column.board.id.eq(boardId))
			.orderBy(orderSpecifier)
			.fetch();
	}

	private OrderSpecifier<?> getOrderSpecifier(Sort sort, QCard card) {
		for (Sort.Order order : sort) {
			PathBuilder<Card> pathBuilder = new PathBuilder<>(card.getType(), card.getMetadata());
			return new OrderSpecifier(
				order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
				pathBuilder.get(order.getProperty())
			);
		}
		return null;
	}
}
