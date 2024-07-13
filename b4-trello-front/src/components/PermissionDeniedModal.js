import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const PermissionDeniedModal = ({ open, onClose }) => {
    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, textAlign: 'center' }}>
                <Typography variant="h6" sx={{ mb: 2 }}>Permission Denied</Typography>
                <Typography variant="body1" sx={{ mb: 2 }}>You do not have permission to perform this action.</Typography>
                <Button variant="contained" color="primary" onClick={onClose}>Close</Button>
            </Box>
        </Modal>
    );
};

export default PermissionDeniedModal;