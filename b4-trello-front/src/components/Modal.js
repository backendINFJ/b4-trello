import React from 'react';
import { Modal as MuiModal, Box, Button } from '@mui/material';

const Modal = ({ isOpen, onClose, children }) => {
    return (
        <MuiModal open={isOpen} onClose={onClose}>
            <Box sx={{
                position: 'absolute',
                top: '50%',
                left: '50%',
                transform: 'translate(-50%, -50%)',
                width: 400,
                bgcolor: 'background.paper',
                boxShadow: 24,
                p: 4,
            }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
                    <Button onClick={onClose} sx={{ color: 'red' }}>●</Button>
                    <Button sx={{ color: 'orange' }}>●</Button>
                    <Button sx={{ color: 'green' }}>●</Button>
                </Box>
                {children}
            </Box>
        </MuiModal>
    );
};

export default Modal;