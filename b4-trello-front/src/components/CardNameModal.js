import React, { useState, useEffect } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const CardNameModal = ({ open, onClose, onSubmit }) => {
    const [cardName, setCardName] = useState('');

    useEffect(() => {
        if (open) {
            setCardName('');
        }
    }, [open]);

    const handleChange = (event) => {
        setCardName(event.target.value);
    };

    const handleSubmit = () => {
        onSubmit(cardName);
        onClose();
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6">Enter Card Name</Typography>
                <TextField
                    value={cardName}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    label="Card Name"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Submit
                </Button>
            </Box>
        </Modal>
    );
};

export default CardNameModal;