import React, { useState, useEffect } from 'react';
import { Box, Typography, Button, IconButton } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import PermissionDeniedModal from './PermissionDeniedModal';
import ColumnNameModal from './ColumnNameModal';
// import { getColumns, createColumn, deleteColumn, updateColumnOrder } from '../api/columnApi';
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

const Board = ({ boardId, boardTitle, boardDescription, isManager, onNameChange }) => {
    const [columns, setColumns] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);
    const [columnModalOpen, setColumnModalOpen] = useState(false);

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

    const handleDeleteColumn = async (columnId) => {
        await deleteColumn(boardId, columnId);
        setColumns(columns.filter(column => column.id !== columnId));
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
        // 실제 API 호출은 주석 처리된 상태
        // await updateColumnOrder(boardId, updatedColumns.map((col, index) => ({ ...col, order: index })));
    };

    return (
        <Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: 2 }}>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                    <Typography variant="h5" sx={{ marginRight: 1 }}>{boardTitle}</Typography>
                    <IconButton onClick={onNameChange} sx={{ padding: '0', marginLeft: '4px' }}>
                        <EditIcon />
                    </IconButton>
                </Box>
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
                                            <Column columnId={column.id} title={column.title} isManager={isManager} />
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
        </Box>
    );
};

export default Board;