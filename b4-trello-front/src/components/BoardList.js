import React, { useState } from 'react';
import { Box, List, ListItem, ListItemText, IconButton, Typography, Button, Modal, TextField } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import CloseIcon from '@mui/icons-material/Close';

const BoardList = ({ boards, onBoardSelect, onBoardUpdate, onBoardDelete, onCreateBoard }) => {
    const [editModalOpen, setEditModalOpen] = useState(false);
    const [editBoardName, setEditBoardName] = useState('');
    const [selectedBoardId, setSelectedBoardId] = useState(null);
    const [newBoardOpen, setNewBoardOpen] = useState(false);
    const [newBoardName, setNewBoardName] = useState('');
    const [newBoardDescription, setNewBoardDescription] = useState('');

    const handleOpenEditModal = (board) => {
        setEditBoardName(board.boardName);
        setSelectedBoardId(board.id);
        setEditModalOpen(true);
    };

    const handleCloseEditModal = () => {
        setEditModalOpen(false);
    };

    const handleUpdateBoard = () => {
        onBoardUpdate(selectedBoardId, { boardName: editBoardName });
        setEditModalOpen(false);
    };

    const handleCreateBoard = () => {
        onCreateBoard({ boardName: newBoardName, description: newBoardDescription });
        setNewBoardOpen(false);
    };

    return (
        <Box sx={{ width: 250, borderRight: '1px solid #ddd', height: '100%', overflowY: 'auto' }}>
            <List>
                {boards.map((board) => (
                    <ListItem button key={board.id} onClick={() => onBoardSelect(board)}>
                        <ListItemText primary={board.boardName} />
                        <IconButton edge="end" onClick={() => handleOpenEditModal(board)}>
                            <EditIcon />
                        </IconButton>
                    </ListItem>
                ))}
            </List>
            <Box sx={{ display: 'flex', justifyContent: 'center', padding: 2 }}>
                <Button variant="contained" color="primary" onClick={() => setNewBoardOpen(true)}>
                    + Create Board
                </Button>
            </Box>
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
                    <Button variant="outlined" color="secondary" onClick={() => onBoardDelete(selectedBoardId)} sx={{ mt: 2 }}>
                        Delete Board
                    </Button>
                </Box>
            </Modal>
            <Modal open={newBoardOpen} onClose={() => setNewBoardOpen(false)}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={() => setNewBoardOpen(false)}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">Create New Board</Typography>
                    <TextField
                        value={newBoardName}
                        onChange={(e) => setNewBoardName(e.target.value)}
                        fullWidth
                        margin="normal"
                        label="Board Name"
                    />
                    <TextField
                        value={newBoardDescription}
                        onChange={(e) => setNewBoardDescription(e.target.value)}
                        fullWidth
                        margin="normal"
                        label="Description"
                    />
                    <Button variant="contained" color="primary" onClick={handleCreateBoard} sx={{ mt: 2 }}>
                        Create
                    </Button>
                </Box>
            </Modal>
        </Box>
    );
};

export default BoardList;