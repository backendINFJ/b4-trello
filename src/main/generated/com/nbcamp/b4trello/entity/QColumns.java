package com.nbcamp.b4trello.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QColumns is a Querydsl query type for Columns
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QColumns extends EntityPathBase<Columns> {

    private static final long serialVersionUID = -1575127046L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QColumns columns = new QColumns("columns");

    public final QBoard board;

    public final NumberPath<Long> columnId = createNumber("columnId", Long.class);

    public final NumberPath<Integer> columnSequence = createNumber("columnSequence", Integer.class);

    public final StringPath columnTitle = createString("columnTitle");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public QColumns(String variable) {
        this(Columns.class, forVariable(variable), INITS);
    }

    public QColumns(Path<? extends Columns> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QColumns(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QColumns(PathMetadata metadata, PathInits inits) {
        this(Columns.class, metadata, inits);
    }

    public QColumns(Class<? extends Columns> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board")) : null;
    }

}

