import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const BoardNameModal = ({ open, onClose, onSubmit, initialName }) => {
    const [boardName, setBoardName] = useState(initialName || '');

    const handleChange = (event) => {
        setBoardName(event.target.value);
    };

    const handleSubmit = () => {
        onSubmit(boardName);
        setBoardName('');
        onClose();
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                {/* 오른쪽 상단에 X 아이콘 버튼 추가 */}
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6">Change Board Name</Typography>
                <TextField
                    value={boardName}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    label="Board Name"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Change
                </Button>
            </Box>
        </Modal>
    );
};

export default BoardNameModal;