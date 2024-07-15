import React from 'react';
import { Modal, Box, Typography, Button, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const ExceptionModal = ({ open, onClose, message }) => {
  return (
      <Modal open={open} onClose={onClose}>
        <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
          <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
            <CloseIcon />
          </IconButton>
          <Typography variant="h6">Error</Typography>
          <Typography variant="body1" sx={{ mb: 2 }}>{message}</Typography>
          <Button variant="contained" color="primary" onClick={onClose}>Close</Button>
        </Box>
      </Modal>
  );
};

export default ExceptionModal;