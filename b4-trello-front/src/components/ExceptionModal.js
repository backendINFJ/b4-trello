import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const ExceptionModal = ({ open, onClose, message }) => {
  return (
      <Modal open={open} onClose={onClose}>
        <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center' }}>
          <Typography variant="h6">Error</Typography>
          <Typography variant="body1" sx={{ mb: 2 }}>{message}</Typography>
          <Button variant="contained" color="primary" onClick={onClose}>Close</Button>
        </Box>
      </Modal>
  );
};

export default ExceptionModal;
