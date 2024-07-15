import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { sendInvite } from '../api/userApi'; // 여기에서 올바르게 임포트

const InvitationForm = ({ open, onClose }) => {
    const [email, setEmail] = useState('');

    const handleChange = (event) => {
        setEmail(event.target.value);
    };

    const handleSubmit = async () => {
        try {
            await sendInvite({ email });
            setEmail('');
            onClose();
        } catch (error) {
            console.error('Failed to send invite:', error);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6" sx={{ mb: 2 }}>Invite User</Typography>
                <TextField
                    value={email}
                    onChange={handleChange}
                    fullWidth
                    margin="normal"
                    label="Email"
                    placeholder="사용자 이메일"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Send Invite
                </Button>
            </Box>
        </Modal>
    );
};

export default InvitationForm;