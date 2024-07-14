import React, { useState } from 'react';
import { Modal, Box, Typography, TextField, Button, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { createUser } from '../api/userApi';
import ExceptionModal from './ExceptionModal';

const SignUpForm = ({ open, onClose }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [error, setError] = useState(null);

    const handleSignUp = async () => {
        try {
            const userData = { username, password, nickname, email };
            await createUser(userData);
            onClose();
            alert('회원 가입이 완료되었습니다.'); // 성공 메시지
        } catch (err) {
            setError(err.message); // 에러 메시지 설정
        }
    };

    return (
        <>
            <Modal open={open} onClose={onClose}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">Sign Up</Typography>
                    <TextField value={username} onChange={(e) => setUsername(e.target.value)} fullWidth margin="normal" label="Username" />
                    <TextField type="password" value={password} onChange={(e) => setPassword(e.target.value)} fullWidth margin="normal" label="Password" />
                    <TextField value={nickname} onChange={(e) => setNickname(e.target.value)} fullWidth margin="normal" label="Nickname" />
                    <TextField value={email} onChange={(e) => setEmail(e.target.value)} fullWidth margin="normal" label="Email" />
                    <Button variant="contained" color="primary" onClick={handleSignUp} sx={{ mt: 2 }}>Sign Up</Button>
                </Box>
            </Modal>
            {error && <ExceptionModal open={Boolean(error)} onClose={() => setError(null)} message={error} />}
        </>
    );
};

export default SignUpForm;