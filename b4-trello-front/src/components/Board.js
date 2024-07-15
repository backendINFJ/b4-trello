import React, { useState, useEffect } from 'react';
import { Box, Typography, Button, TextField, IconButton, Modal } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import CloseIcon from '@mui/icons-material/Close';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import PermissionDeniedModal from './PermissionDeniedModal';
import ColumnNameModal from './ColumnNameModal';
import Column from './Column';

const Board = ({ boardId, boardTitle, boardDescription, isManager, userRole, onNameChange, onDelete, onInvite, columns }) => {
    const [columnData, setColumnData] = useState(columns);
    const [modalOpen, setModalOpen] = useState(false);
    const [columnModalOpen, setColumnModalOpen] = useState(false);
    const [editModalOpen, setEditModalOpen] = useState(false);
    const [editBoardName, setEditBoardName] = useState(boardTitle);

    useEffect(() => {
        setColumnData(columns);
    }, [columns]);

    const handleAddColumn = (columnName) => {
        const addNewColumn = async () => {
            const newColumn = { id: Math.random().toString(), title: columnName };
            setColumnData([...columnData, newColumn]);
        };
        addNewColumn();
    };

    const handleDeleteColumn = (columnId) => {
        setColumnData(columnData.filter(column => column.id !== columnId));
    };

    const onDragEnd = (result) => {
        if (!result.destination) return;

        if (!isManager) {
            setModalOpen(true);
            return;
        }

        const updatedColumns = [...columnData];
        const [movedColumn] = updatedColumns.splice(result.source.index, 1);
        updatedColumns.splice(result.destination.index, 0, movedColumn);

        setColumnData(updatedColumns);
    };

    const handleCloseEditModal = () => {
        setEditModalOpen(false);
    };

    const handleUpdateBoard = () => {
        onNameChange({ boardName: editBoardName, description: boardDescription });
        setEditModalOpen(false);
    };

    return (
        <Box>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: 2 }}>
                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                    <Typography variant="h5" sx={{ marginRight: 1 }}>{boardTitle}</Typography>
                    <Typography variant="body2" sx={{ marginRight: 2 }}>{userRole}</Typography>
                    {isManager && (
                        <IconButton onClick={() => setEditModalOpen(true)} sx={{ padding: '0', marginLeft: '4px' }}>
                            <EditIcon />
                        </IconButton>
                    )}
                </Box>
                {isManager && (
                    <Button variant="outlined" color="secondary" onClick={onDelete}>
                        Delete Board
                    </Button>
                )}
            </Box>
            <Typography variant="body2" sx={{ marginBottom: 2 }}>{boardDescription}</Typography>
            {isManager && (
                <Button onClick={() => setColumnModalOpen(true)} sx={{ margin: 2 }}>Add Column</Button>
            )}
            <DragDropContext onDragEnd={onDragEnd}>
                <Droppable droppableId="droppable-board" direction="horizontal">
                    {(provided) => (
                        <Box display="flex" {...provided.droppableProps} ref={provided.innerRef} sx={{ overflowX: 'auto' }}>
                            {columnData.map((column, index) => (
                                <Draggable key={column.id} draggableId={column.id} index={index}>
                                    {(provided) => (
                                        <Box
                                            ref={provided.innerRef}
                                            {...provided.draggableProps}
                                            {...provided.dragHandleProps}
                                            sx={{ margin: 2 }}
                                        >
                                            <Column columnId={column.id} title={column.title} isManager={isManager} onDeleteColumn={() => handleDeleteColumn(column.id)} />
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
            <Modal open={editModalOpen} onClose={handleCloseEditModal}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={handleCloseEditModal}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">Edit Board</Typography>
                    <TextField
                        value={editBoardName}
                        onChange={(e) => setEditBoardName(e.target.value)}
                        fullWidth
                        margin="normal"
                        label="Board Name"
                    />
                    <Button variant="contained" color="primary" onClick={handleUpdateBoard} sx={{ mt: 2 }}>
                        Update
                    </Button>
                    <Button variant="outlined" color="secondary" onClick={onDelete} sx={{ mt: 2 }}>
                        Delete Board
                    </Button>
                </Box>
            </Modal>
        </Box>
    );
};

export default Board;