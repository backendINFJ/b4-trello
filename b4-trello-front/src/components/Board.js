import React, { useState, useEffect } from 'react';
import { Box, Typography, Modal, Button, TextField, IconButton, Menu, MenuItem } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import Column from './Column';
import PermissionDeniedModal from './PermissionDeniedModal';
import { getColumns, createColumn, updateColumnSequence, deleteColumn } from '../api/columnApi';
import { updateBoardName } from '../api/boardApi';

const Board = ({ boardId, boardTitle, boardDescription, isManager, isSample }) => {
    const [columns, setColumns] = useState([]);
    const [columnModalOpen, setColumnModalOpen] = useState(false);
    const [newColumnName, setNewColumnName] = useState('');
    const [boardName, setBoardName] = useState(boardTitle);
    const [boardNameModalOpen, setBoardNameModalOpen] = useState(false);
    const [anchorEl, setAnchorEl] = useState(null);
    const [permissionDeniedOpen, setPermissionDeniedOpen] = useState(false);

    useEffect(() => {
        if (isSample) {
            setColumns(boardDescription.columns);
        } else {
            const fetchColumns = async () => {
                const data = await getColumns(boardId);
                setColumns(data);
            };
            fetchColumns();
        }
    }, [boardId, boardDescription, isSample]);

    const checkPermission = () => {
        if (!isManager) {
            setPermissionDeniedOpen(true);
            return false;
        }
        return true;
    };

    const handleAddColumn = async () => {
        if (!checkPermission()) return;

        if (columns.some(column => column.title === newColumnName)) {
            alert('Column name already exists. Please choose a different name.');
            return;
        }
        const newColumn = await createColumn(boardId, { title: newColumnName });
        setColumns([...columns, newColumn]);
        setColumnModalOpen(false);
        setNewColumnName('');
    };

    const handleDeleteColumn = async (columnId) => {
        if (!checkPermission()) return;

        await deleteColumn(columnId);
        setColumns(columns.filter(column => column.id !== columnId));
    };

    const moveColumn = (fromIndex, toIndex) => {
        if (toIndex < 0 || toIndex >= columns.length) return;
        const updatedColumns = [...columns];
        const [movedColumn] = updatedColumns.splice(fromIndex, 1);
        updatedColumns.splice(toIndex, 0, movedColumn);
        setColumns(updatedColumns);
    };

    const handleBoardNameChange = async () => {
        if (!checkPermission()) return;

        const updatedBoard = await updateBoardName(boardId, boardName);
        setBoardName(updatedBoard.name);
        setBoardNameModalOpen(false);
    };

    const handleMenuClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    return (
        <Box sx={{ margin: 2, backgroundColor: '#f4f4f4', borderRadius: 2, width: 300 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: 2, backgroundColor: '#e0e0e0', borderTopLeftRadius: 2, borderTopRightRadius: 2 }}>
                <Typography variant="h6" sx={{ flexGrow: 1, textAlign: 'left' }}>{boardName}</Typography>
                <IconButton onClick={handleMenuClick}><MoreVertIcon /></IconButton>
                <Menu
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl)}
                    onClose={handleMenuClose}
                >
                    <MenuItem onClick={() => setBoardNameModalOpen(true)}>Edit Board</MenuItem>
                    <MenuItem onClick={() => setColumnModalOpen(true)}>Create Column</MenuItem>
                </Menu>
            </Box>
            <DragDropContext onDragEnd={() => { }}>
                <Droppable droppableId={`droppable-${boardId}`} direction="horizontal">
                    {(provided) => (
                        <Box {...provided.droppableProps} ref={provided.innerRef} sx={{ display: 'flex', overflowX: 'auto' }}>
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
                                                deleteColumn={handleDeleteColumn}
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
            <Modal open={columnModalOpen} onClose={() => setColumnModalOpen(false)}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={() => setColumnModalOpen(false)}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">Create Column</Typography>
                    <TextField
                        value={newColumnName}
                        onChange={(e) => setNewColumnName(e.target.value)}
                        fullWidth
                        margin="normal"
                        label="Column Name"
                    />
                    <Button variant="contained" color="primary" onClick={handleAddColumn} sx={{ mt: 2 }}>
                        Create
                    </Button>
                </Box>
            </Modal>
            <Modal open={boardNameModalOpen} onClose={() => setBoardNameModalOpen(false)}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={() => setBoardNameModalOpen(false)}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">Edit Board Name</Typography>
                    <TextField
                        value={boardName}
                        onChange={(e) => setBoardName(e.target.value)}
                        fullWidth
                        margin="normal"
                        label="Board Name"
                    />
                    <Button variant="contained" color="primary" onClick={handleBoardNameChange} sx={{ mt: 2 }}>
                        Save
                    </Button>
                </Box>
            </Modal>
            <PermissionDeniedModal open={permissionDeniedOpen} onClose={() => setPermissionDeniedOpen(false)} />
        </Box>
    );
};

export default Board;