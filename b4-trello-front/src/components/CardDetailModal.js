import React, { useEffect } from 'react';
import { Modal, Box, Typography, IconButton, Button } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import CommentIcon from '@mui/icons-material/Comment';
import DeleteIcon from '@mui/icons-material/Delete';

const CardDetailModal = ({ open, onClose, card, onDelete }) => {

    useEffect(() => {
        if (open && !card) {
            onClose();
        }
    }, [open, card, onClose]);

    if (!card) {
        return null;
    }

    const handleDelete = () => {
        onDelete(card.id);
        onClose();
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 400, mx: 'auto', mt: '10vh', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6" sx={{ mb: 2, textAlign: 'center' }}>Card Details</Typography>
                <Typography variant="body1" sx={{ mb: 2, textAlign: 'left' }}>Title: {card.title}</Typography>
                <Typography variant="body1" sx={{ mb: 2, textAlign: 'left' }}>Content: {card.content}</Typography>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                        <CommentIcon />
                        <Typography sx={{ ml: 1 }}>Comments</Typography>
                    </Box>
                    <IconButton size="small" onClick={handleDelete}>
                        <DeleteIcon />
                    </IconButton>
                </Box>
                <Typography variant="body2" sx={{ mb: 2, textAlign: 'left' }}>Comment content here...</Typography>
                <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
                    <Button variant="contained" color="primary">Edit</Button>
                    <Button variant="contained" color="secondary" onClick={handleDelete}>Delete</Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default CardDetailModal;