import React, { useState, useEffect } from 'react';
import { Modal, Box, Typography, Button, TextField, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import { getComments, createComment, deleteComment } from '../api/commentApi';

const CardDetailModal = ({ open, onClose, cardId, cardName, cardContent, onUpdate, onDelete }) => {
    const [content, setContent] = useState(cardContent);
    const [newComment, setNewComment] = useState('');
    const [comments, setComments] = useState([]);

    useEffect(() => {
        if (open) {
            const fetchComments = async () => {
                try {
                    const data = await getComments(cardId);
                    setComments(data);
                } catch (error) {
                    console.error('Error fetching comments:', error);
                }
            };
            fetchComments();
        }
    }, [open, cardId]);

    const handleUpdate = () => {
        onUpdate(cardId, content);
    };

    const handleDelete = () => {
        onDelete(cardId);
    };

    const handleAddComment = async () => {
        try {
            const comment = await createComment(cardId, newComment);
            setComments([...comments, comment]);
            setNewComment('');
        } catch (error) {
            console.error('Error adding comment:', error);
        }
    };

    const handleDeleteComment = async (commentId) => {
        try {
            await deleteComment(commentId);
            setComments(comments.filter(comment => comment.id !== commentId));
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    };

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 500, mx: 'auto', mt: '10vh', textAlign: 'center', position: 'relative' }}>
                <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={onClose}>
                    <CloseIcon />
                </IconButton>
                <Typography variant="h6">카드 상세</Typography>
                <Typography sx={{ mt: 2, mb: 1 }}>Card Name: {cardName}</Typography>
                <TextField
                    label="내용 상세"
                    multiline
                    rows={4}
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    fullWidth
                    margin="normal"
                />
                <Button variant="contained" color="primary" onClick={handleUpdate} sx={{ mt: 2, mr: 1 }}>
                    수정
                </Button>
                <Button variant="contained" color="secondary" onClick={handleDelete} sx={{ mt: 2 }}>
                    삭제
                </Button>
                <Box sx={{ mt: 4 }}>
                    <Typography variant="h6">댓글</Typography>
                    <TextField
                        label="댓글 입력"
                        multiline
                        rows={2}
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                        fullWidth
                        margin="normal"
                    />
                    <Button variant="contained" color="primary" onClick={handleAddComment} sx={{ mt: 2 }}>
                        댓글 등록
                    </Button>
                    <Box sx={{ mt: 2 }}>
                        {comments.map(comment => (
                            <Box key={comment.id} sx={{ display: 'flex', alignItems: 'center', mt: 2 }}>
                                <Typography sx={{ flexGrow: 1 }}>{comment.content}</Typography>
                                <IconButton onClick={() => handleDeleteComment(comment.id)}>
                                    <CloseIcon />
                                </IconButton>
                            </Box>
                        ))}
                    </Box>
                </Box>
            </Box>
        </Modal>
    );
};

export default CardDetailModal;