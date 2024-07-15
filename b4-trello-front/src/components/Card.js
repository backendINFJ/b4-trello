// src/components/Card.js

import React, { useState } from 'react';
import { Box, Paper, Typography, IconButton, Menu, MenuItem, Modal, TextField, Button } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import CloseIcon from '@mui/icons-material/Close';
import { updateCard, deleteCard } from '../api/cardApi';

const Card = ({ card, columnId, isManager, onUpdate, onDelete }) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [editModalOpen, setEditModalOpen] = useState(false);
    const [editedTitle, setEditedTitle] = useState(card.title);
    const [editedContent, setEditedContent] = useState(card.content);

    const handleMenuClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleEdit = () => {
        setEditModalOpen(true);
        handleMenuClose();
    };

    const handleDelete = async () => {
        await deleteCard(columnId, card.id);
        onDelete(card.id);
        handleMenuClose();
    };

    const handleSave = async () => {
        const updatedCard = await updateCard(columnId, card.id, { title: editedTitle, content: editedContent });
        onUpdate(updatedCard.data);
        setEditModalOpen(false);
    };

    return (
        <Paper sx={{ margin: 1, padding: 2, backgroundColor: '#ffffff' }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <Typography variant="body1" sx={{ flexGrow: 1 }}>{card.title}</Typography>
                {isManager && (
                    <>
                        <IconButton onClick={handleMenuClick} size="small">
                            <MoreVertIcon />
                        </IconButton>
                        <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleMenuClose}>
                            <MenuItem onClick={handleEdit}>Edit</MenuItem>
                            <MenuItem onClick={handleDelete}>Delete</MenuItem>
                        </Menu>
                    </>
                )}
            </Box>
            <Typography variant="body2">{card.content}</Typography>
            <Modal open={editModalOpen} onClose={() => setEditModalOpen(false)}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={() => setEditModalOpen(false)}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">Edit Card</Typography>
                    <TextField value={editedTitle} onChange={(e) => setEditedTitle(e.target.value)} fullWidth margin="normal" label="Card Title" />
                    <TextField value={editedContent} onChange={(e) => setEditedContent(e.target.value)} fullWidth margin="normal" label="Card Content" />
                    <Button variant="contained" color="primary" onClick={handleSave} sx={{ mt: 2 }}>Save</Button>
                </Box>
            </Modal>
        </Paper>
    );
};

export default Card;
