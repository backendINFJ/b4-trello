import React, { useState } from 'react';
import { Modal, Box, Typography, TextField, Button, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import ChatBubbleOutlineIcon from '@mui/icons-material/ChatBubbleOutline';

const CardDetailModal = ({ open, onClose, card, onUpdate, onDelete }) => {
    const [title, setTitle] = useState(card.title);
    const [content, setContent] = useState(card.content);
    const [comments, setComments] = useState(card.comments || []);
    const [newComment, setNewComment] = useState('');

    const handleUpdate = () => {
        onUpdate({ ...card, title, content });
    };

    const handleDelete = () => {
        onDelete(card.id);
    };

    const handleAddComment = () => {
        if (newComment.trim()) {
            setComments([...comments, newComment]);
            setNewComment('');
        }
    };

    const handleDeleteComment = (index) => {
        setComments(comments.filter((_, i) => i !== index));
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{
                p: 4,
                bgcolor: 'white',
                borderRadius: 1,
                width: '400px',
                mx: 'auto',
                mt: '10vh',
                textAlign: 'center',
                position: 'relative'
            }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6" sx={{ mb: 2 }}>카드 상세</Typography>
                <TextField
                    label="Card Name"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <TextField
                    label="내용 상세"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    fullWidth
                    margin="normal"
                    multiline
                    rows={4}
                />
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
                    <Button variant="contained" color="primary" onClick={handleUpdate}>수정</Button>
                    <Button variant="contained" color="error" onClick={handleDelete}>삭제</Button>
                </Box>
                <Box sx={{ display: 'flex', alignItems: 'center', mt: 4 }}>
                    <ChatBubbleOutlineIcon color="primary" />
                    <Typography variant="body1" sx={{ ml: 1 }}>댓글</Typography>
                </Box>
                <Box sx={{ mt: 2, textAlign: 'left' }}>
                    {comments.map((comment, index) => (
                        <Box key={index} sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 1 }}>
                            <Typography variant="body2">{comment}</Typography>
                            <IconButton size="small" onClick={() => handleDeleteComment(index)}>
                                <CloseIcon fontSize="small" />
                            </IconButton>
                        </Box>
                    ))}
                </Box>
                <TextField
                    label="댓글"
                    value={newComment}
                    onChange={(e) => setNewComment(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
                    <Button variant="contained" color="primary" onClick={handleAddComment}>댓글 등록</Button>
                    <Button variant="contained" color="error" onClick={handleDeleteComment}>삭제</Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default CardDetailModal;