// src/components/ProfileEditModal.js
import React, { useState, useEffect } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { updateUser, deleteUser } from '../api/userApi';

const ProfileEditModal = ({ open, onClose, user }) => {
  const [username, setUsername] = useState(user?.username || '');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState(user?.email || '');
  const [nickname, setNickname] = useState(user?.nickname || '');

  useEffect(() => {
    if (open) {
      setUsername(user?.username || '');
      setPassword('');
      setEmail(user?.email || '');
      setNickname(user?.nickname || '');
    }
  }, [open, user]);

  const handleUpdate = async () => {
    try {
      await updateUser(user.id, { username, password, email, nickname });
      onClose();
    } catch (error) {
      console.error('Failed to update user:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await deleteUser(user.id);
      onClose();
    } catch (error) {
      console.error('Failed to delete user:', error);
    }
  };

  return (
      <Modal open={open} onClose={onClose}>
        <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
          <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
            <CloseIcon />
          </IconButton>
          <Typography variant="h6" sx={{ mb: 2, userSelect: 'none' }}>Edit Profile</Typography>
          <TextField
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              fullWidth
              margin="normal"
              label="Username"
              placeholder="아이디"
              sx={{ width: 270, height: 50 }}
              inputProps={{ style: { fontSize: 10, padding: 18 } }}
          />
          <TextField
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              fullWidth
              margin="normal"
              label="Password"
              placeholder="비밀번호는 특수문자포함 최소 8자, 최대 15자입니다."
              sx={{ width: 270, height: 50 }}
              inputProps={{ style: { fontSize: 10, padding: 18 } }}
          />
          <TextField
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              fullWidth
              margin="normal"
              label="Email"
              placeholder="이메일"
              sx={{ width: 270, height: 50 }}
              inputProps={{ style: { fontSize: 10, padding: 18 } }}
          />
          <TextField
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
              fullWidth
              margin="normal"
              label="Nickname"
              placeholder="닉네임"
              sx={{ width: 270, height: 50 }}
              inputProps={{ style: { fontSize: 10, padding: 18 } }}
          />
          <Box sx={{ mt: 2, display: 'flex', justifyContent: 'space-between' }}>
            <Button variant="contained" color="secondary" onClick={handleDelete}>
              Delete Account
            </Button>
            <Button variant="contained" color="primary" onClick={handleUpdate}>
              Update Profile
            </Button>
          </Box>
        </Box>
      </Modal>
  );
};

export default ProfileEditModal;