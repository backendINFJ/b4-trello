import React, { useState } from 'react';
import { Modal, Box, Typography, TextField, Button, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import ExceptionModal from './ExceptionModal';

const LoginForm = ({ open, onClose, onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    const handleLogin = async () => {
        try {
            const userData = { username, password };
            await onLogin(userData);
            onClose();
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
                    <Typography variant="h6">Login</Typography>
                    <TextField value={username} onChange={(e) => setUsername(e.target.value)} fullWidth margin="normal" label="Username" />
                    <TextField type="password" value={password} onChange={(e) => setPassword(e.target.value)} fullWidth margin="normal" label="Password" />
                    <Button variant="contained" color="primary" onClick={handleLogin} sx={{ mt: 2 }}>Login</Button>
                </Box>
            </Modal>
            {error && <ExceptionModal open={Boolean(error)} onClose={() => setError(null)} message={error} />}
        </>
    );
};

export default LoginForm;