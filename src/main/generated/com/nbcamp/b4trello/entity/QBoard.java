package com.nbcamp.b4trello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -883019197L;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardName = createString("boardName");

    public final ListPath<Columns, QColumns> columns = this.<Columns, QColumns>createList("columns", Columns.class, QColumns.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<UserBoard, QUserBoard> userBoards = this.<UserBoard, QUserBoard>createList("userBoards", UserBoard.class, QUserBoard.class, PathInits.DIRECT2);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

