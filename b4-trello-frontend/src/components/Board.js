import React, { useState, useEffect } from 'react';
import { Box, Button } from '@mui/material';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
// import { getColumns, createColumn, deleteColumn, updateColumnOrder } from '../api/columnApi';
import Column from './Column';

// 더미 함수들 추가
const getColumns = async (boardId) => {
    return [
        { id: '1', title: 'Sample Column 1' },
        { id: '2', title: 'Sample Column 2' }
    ];
};

const createColumn = async (boardId, column) => {
    return { id: Math.random().toString(), ...column };
};

const deleteColumn = async (boardId, columnId) => {
    return true;
};

const updateColumnOrder = async (boardId, columns) => {
    return true;
};

const Board = ({ boardId }) => {
    const [columns, setColumns] = useState([]);

    useEffect(() => {
        const fetchColumns = async () => {
            const data = await getColumns(boardId);
            setColumns(data);
        };
        fetchColumns();
    }, [boardId]);

    const handleAddColumn = async () => {
        const newColumn = await createColumn(boardId, { title: 'New Column' });
        setColumns([...columns, newColumn]);
    };

    const handleDeleteColumn = async (columnId) => {
        await deleteColumn(boardId, columnId);
        setColumns(columns.filter(column => column.id !== columnId));
    };

    const onDragEnd = (result) => {
        if (!result.destination) return;

        const updatedColumns = [...columns];
        const [movedColumn] = updatedColumns.splice(result.source.index, 1);
        updatedColumns.splice(result.destination.index, 0, movedColumn);

        setColumns(updatedColumns);
        // 실제 API 호출은 주석 처리된 상태
        // await updateColumnOrder(boardId, updatedColumns.map((col, index) => ({ ...col, order: index })));
    };

    return (
        <Box>
            <Button onClick={handleAddColumn} sx={{ margin: 2 }}>Add Column</Button>
            <DragDropContext onDragEnd={onDragEnd}>
                <Droppable droppableId="droppable-board" direction="horizontal">
                    {(provided) => (
                        <Box display="flex" {...provided.droppableProps} ref={provided.innerRef} sx={{ overflowX: 'auto' }}>
                            {columns.map((column, index) => (
                                <Draggable key={column.id} draggableId={column.id} index={index}>
                                    {(provided) => (
                                        <Box
                                            ref={provided.innerRef}
                                            {...provided.draggableProps}
                                            {...provided.dragHandleProps}
                                            sx={{ margin: 2 }}
                                        >
                                            <Column columnId={column.id} />
                                        </Box>
                                    )}
                                </Draggable>
                            ))}
                            {provided.placeholder}
                        </Box>
                    )}
                </Droppable>
            </DragDropContext>
        </Box>
    );
};

export default Board;