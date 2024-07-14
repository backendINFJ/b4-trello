package com.nbcamp.b4trello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBoard is a Querydsl query type for UserBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBoard extends EntityPathBase<UserBoard> {

    private static final long serialVersionUID = 590087864L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBoard userBoard = new QUserBoard("userBoard");

    public final QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public final EnumPath<UserType> userType = createEnum("userType", UserType.class);

    public QUserBoard(String variable) {
        this(UserBoard.class, forVariable(variable), INITS);
    }

    public QUserBoard(Path<? extends UserBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBoard(PathMetadata metadata, PathInits inits) {
        this(UserBoard.class, metadata, inits);
    }

    public QUserBoard(Class<? extends UserBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

