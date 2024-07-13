import React, { useState, useEffect } from 'react';
import { Box, Button, Typography } from '@mui/material';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import PermissionDeniedModal from './PermissionDeniedModal';
import ColumnNameModal from './ColumnNameModal';
import DeleteColumnModal from './DeleteColumnModal';
import Column from './Column';

// 더미 함수들 추가
const getColumns = async (boardId) => {
    return [
        { id: '1', title: 'Upcoming' },
        { id: '2', title: 'In Progress' },
        { id: '3', title: 'Done' },
        { id: '4', title: 'Emergency' }
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

const Board = ({ boardId, isManager }) => {
    const [columns, setColumns] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);
    const [columnModalOpen, setColumnModalOpen] = useState(false);
    const [deleteColumnModalOpen, setDeleteColumnModalOpen] = useState(false);
    const [columnToDelete, setColumnToDelete] = useState(null);

    useEffect(() => {
        const fetchColumns = async () => {
            const data = await getColumns(boardId);
            setColumns(data);
        };
        fetchColumns();
    }, [boardId]);

    const handleAddColumn = (columnName) => {
        const addNewColumn = async () => {
            const newColumn = await createColumn(boardId, { title: columnName });
            setColumns([...columns, newColumn]);
        };
        addNewColumn();
    };

    const confirmDeleteColumn = (columnId) => {
        setColumnToDelete(columnId);
        setDeleteColumnModalOpen(true);
    };

    const handleDeleteColumn = async (columnId) => {
        await deleteColumn(boardId, columnId);
        setColumns(columns.filter(column => column.id !== columnId));
        setDeleteColumnModalOpen(false);
    };

    const moveColumn = (fromIndex, toIndex) => {
        if (toIndex < 0 || toIndex >= columns.length) return;
        const updatedColumns = [...columns];
        const [movedColumn] = updatedColumns.splice(fromIndex, 1);
        updatedColumns.splice(toIndex, 0, movedColumn);
        setColumns(updatedColumns);
        updateColumnOrder(boardId, updatedColumns);
    };

    const onDragEnd = (result) => {
        if (!result.destination) return;

        if (!isManager) {
            setModalOpen(true);
            return;
        }

        const updatedColumns = [...columns];
        const [movedColumn] = updatedColumns.splice(result.source.index, 1);
        updatedColumns.splice(result.destination.index, 0, movedColumn);

        setColumns(updatedColumns);
        updateColumnOrder(boardId, updatedColumns.map((col, index) => ({ ...col, order: index })));
    };

    return (
        <Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: 2 }}>
                <Typography variant="h5">보드 제목</Typography>
            </Box>
            <Button onClick={() => setColumnModalOpen(true)} sx={{ margin: 2 }}>Add Column</Button>
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
                                            <Column
                                                columnId={column.id}
                                                title={column.title}
                                                isManager={isManager}
                                                index={index}
                                                moveColumn={moveColumn}
                                                deleteColumn={confirmDeleteColumn}
                                            />
                                        </Box>
                                    )}
                                </Draggable>
                            ))}
                            {provided.placeholder}
                        </Box>
                    )}
                </Droppable>
            </DragDropContext>
            <PermissionDeniedModal open={modalOpen} onClose={() => setModalOpen(false)} />
            <ColumnNameModal open={columnModalOpen} onClose={() => setColumnModalOpen(false)} onSubmit={handleAddColumn} />
            <DeleteColumnModal open={deleteColumnModalOpen} onClose={() => setDeleteColumnModalOpen(false)} onDelete={() => handleDeleteColumn(columnToDelete)} />
        </Box>
    );
};

export default Board;