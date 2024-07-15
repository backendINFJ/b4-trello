// src/components/SignUpModal.js
import React, { useState } from 'react';
import { Modal, Box, Typography, Button, TextField, Snackbar, Alert } from '@mui/material';
import { createUser } from '../api/authApi';

const SignUpModal = ({ open, onClose, onSignUp }) => {
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
      await createUser({ username, password, email, nickname });
      setSnackbarMessage('회원가입 성공!');
      setSnackbarSeverity('success');
      onSignUp({ username, password });
      onClose();
    } catch (error) {
      setSnackbarMessage(error.message || '회원가입 실패');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    }
  };

  return (
      <Modal open={open} onClose={onClose}>
        <Box sx={modalStyle}>
          <Typography variant="h6">Sign Up</Typography>
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
          <Snackbar
              open={snackbarOpen}
              autoHideDuration={6000}
              onClose={handleSnackbarClose}
              anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
              sx={{ '& .MuiSnackbarContent-root': { backgroundColor: 'rgba(255, 0, 0, 0.8)' } }}
          >
            <Alert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
              {snackbarMessage}
            </Alert>
          </Snackbar>
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

export default SignUpModal;