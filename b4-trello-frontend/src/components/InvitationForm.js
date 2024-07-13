import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const InvitationForm = ({ open, onClose }) => {
    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1 }}>
                <Typography variant="h6">Invite</Typography>
                <Button onClick={onClose}>Close</Button>
            </Box>
        </Modal>
    );
};

export default InvitationForm;