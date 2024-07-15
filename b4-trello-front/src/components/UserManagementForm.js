import React, { useState } from 'react';
import { Modal, Box, Typography, Button, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import ProfileEditModal from './ProfileEditModal';

const UserManagementForm = ({ open, onClose, user }) => {
    const [editProfileOpen, setEditProfileOpen] = useState(false);

    const handleEditProfileClose = () => {
        setEditProfileOpen(false);
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6" sx={{ mb: 2, userSelect: 'none' }}>User Management</Typography>
                <Button variant="contained" color="primary" sx={{ mb: 2, width: '100%' }} onClick={() => setEditProfileOpen(true)}>
                    Change Profile
                </Button>
                <Button variant="contained" color="primary" sx={{ width: '100%' }}>
                    My Boards
                </Button>
            </Box>
            <ProfileEditModal open={editProfileOpen} onClose={handleEditProfileClose} user={user} />
        </Modal>
    );
};

export default UserManagementForm;