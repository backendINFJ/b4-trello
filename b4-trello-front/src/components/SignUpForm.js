import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton, Snackbar, Alert } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { createUser } from '../api/userApi';

const SignUpForm = ({ open, onClose, onSignUp }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [nickname, setNickname] = useState('');
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    const handleSubmit = async () => {
        try {
            console.log('Trying to create user with:', { username, password, email, nickname });
            const response = await createUser({ username, password, email, nickname });
            console.log('User created successfully:', response);
            onSignUp({ username, password, email, nickname });
            setSnackbarMessage('회원가입 성공');
            setSnackbarSeverity('success');
            setUsername('');
            setPassword('');
            setEmail('');
            setNickname('');
            onClose();
        } catch (error) {
            console.error('Failed to sign up:', error);
            setSnackbarMessage(`회원가입 실패: ${error.response.data.message}`);
            setSnackbarSeverity('error');
        } finally {
            setSnackbarOpen(true);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6" sx={{ mb: 2 }}>Sign Up</Typography>
                <TextField
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Username"
                    placeholder="아이디는 최소 4자, 최대 10자입니다."
                />
                <TextField
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Password"
                    placeholder="비밀번호는 특수문자포함 최소 8자, 최대 15자입니다."
                />
                <TextField
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Email"
                    placeholder="이메일"
                />
                <TextField
                    value={nickname}
                    onChange={(e) => setNickname(e.target.value)}
                    fullWidth
                    margin="normal"
                    label="Nickname"
                    placeholder="닉네임"
                />
                <Button variant="contained" color="primary" onClick={handleSubmit} sx={{ mt: 2 }}>
                    Sign Up
                </Button>
                <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={handleSnackbarClose}>
                    <Alert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
                        {snackbarMessage}
                    </Alert>
                </Snackbar>
            </Box>
        </Modal>
    );
};

export default SignUpForm;