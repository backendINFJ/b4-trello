import React from 'react';
import { Modal, Box, Typography, Button, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const UserManagementForm = ({ open, onClose }) => {
    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6" sx={{ mb: 2 }}>User Management</Typography>
                <Button variant="contained" color="primary" sx={{ mb: 2, width: '100%' }}>Change Profile</Button>
                <Button variant="contained" color="primary" sx={{ width: '100%' }}>My Boards</Button>
            </Box>
        </Modal>
    );
};

export default UserManagementForm;