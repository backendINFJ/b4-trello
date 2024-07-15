import React from 'react';
import { Modal, Box, Typography, Button } from '@mui/material';

const PermissionDeniedModal = ({ open, onClose }) => {
  return (
      <Modal open={open} onClose={onClose}>
        <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center' }}>
          <Typography variant="h6" gutterBottom>권한이 없습니다</Typography>
          <Button variant="contained" color="primary" onClick={onClose}>닫기</Button>
        </Box>
      </Modal>
  );
};

export default PermissionDeniedModal;