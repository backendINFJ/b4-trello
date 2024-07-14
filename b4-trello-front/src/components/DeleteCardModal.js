import React from 'react';
import { Modal, Box, Typography, Button, IconButton } from '@mui/material';
import CloseIcon from "@mui/icons-material/Close";

const DeleteCardModal = ({ open, onClose, onDelete }) => {
    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6">Delete Card</Typography>
                <Typography variant="body1" sx={{ mb: 2 }}>Are you sure you want to delete this card?</Typography>
                <Box sx={{ mt: 2, display: 'flex', justifyContent: 'space-between' }}>
                    <Button variant="contained" color="secondary" onClick={onClose}>
                        No
                    </Button>
                    <Button variant="contained" color="primary" onClick={onDelete}>
                        Yes
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default DeleteCardModal;