import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const DeleteColumnModal = ({ open, onClose, onDelete }) => {
    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center' }}>
                <Typography variant="h6">Delete Column</Typography>
                <Typography variant="body1" sx={{ mb: 2 }}>Are you sure you want to delete this column?</Typography>
                <Box sx={{ mt: 2, display: 'flex', justifyContent: 'space-between' }}>
                    <Button variant="contained" color="secondary" onClick={onClose}>
                        No
                    </Button>
                    <Button variant="contained" color="primary" onClick={onDelete}>
                        Yes
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default DeleteColumnModal;