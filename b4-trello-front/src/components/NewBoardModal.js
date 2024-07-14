import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const NewBoardModal = ({ open, onClose, onSubmit }) => {
    const [boardName, setBoardName] = useState('');
    const [description, setDescription] = useState('');

    const handleChangeName = (event) => {
        setBoardName(event.target.value);
    };

    const handleChangeDescription = (event) => {
        setDescription(event.target.value);
    };

    const handleSubmit = () => {
        onSubmit({ title: boardName, description });
        setBoardName('');
        setDescription('');
        onClose();
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                {/* 오른쪽 상단에 X 아이콘 버튼 추가 */}
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6">Create New Board</Typography>
                <TextField
                    value={boardName}
                    onChange={handleChangeName}
                    fullWidth
                    margin="normal"
                    label="Board Name"
                />
                <TextField
                    value={description}
                    onChange={handleChangeDescription}
                    fullWidth
                    margin="normal"
                    label="Description"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Create
                </Button>
            </Box>
        </Modal>
    );
};

export default NewBoardModal;