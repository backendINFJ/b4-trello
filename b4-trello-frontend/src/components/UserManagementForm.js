import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const UserManagementForm = ({ open, onClose }) => {
    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1 }}>
                <Typography variant="h6">User Management</Typography>
                <Button onClick={onClose}>Close</Button>
            </Box>
        </Modal>
    );
};

export default UserManagementForm;