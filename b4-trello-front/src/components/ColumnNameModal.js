import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const ColumnNameModal = ({ open, onClose, onSubmit }) => {
    const [columnName, setColumnName] = useState('');

    const handleSubmit = () => {
        onSubmit(columnName);
        setColumnName('');
        onClose();
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6">Add New Column</Typography>
                <TextField
                    value={columnName}
                    onChange={(e) => setColumnName(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Column Name"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Add Column
                </Button>
            </Box>
        </Modal>
    );
};

export default ColumnNameModal;