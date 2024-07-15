import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField } from '@mui/material';

const LoginModal = ({ open, onClose, onLogin, openSignUp }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async () => {
        await onLogin({ username, password });
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ ...modalStyle }}>
                <Typography variant="h6">Login</Typography>
                <TextField
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Username"
                    placeholder="Enter your username"
                />
                <TextField
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Password"
                    placeholder="Enter your password"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Login
                </Button>
                <Typography variant="body2" sx={{ mt: 2, cursor: 'pointer' }} onClick={openSignUp}>
                    Don't have an account? Sign Up
                </Typography>
            </Box>
        </Modal>
    );
};

const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

export default LoginModal;