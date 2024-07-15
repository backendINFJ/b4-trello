// src/components/BoardList.js
import React, { useState, useContext, useEffect } from 'react';
import { Box, Typography, Button, IconButton, Modal, TextField } from '@mui/material';
import AddIcon from '@mui/icons-material/Add';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import { createBoard, updateBoard, fetchBoards } from '../api/boardApi';
import { AuthContext } from '../context/AuthContext';

const BoardList = ({ onBoardCreate, onBoardUpdate, boards }) => {
    const { user } = useContext(AuthContext);
    const [openCreateModal, setOpenCreateModal] = useState(false);
    const [openEditModal, setOpenEditModal] = useState(false);
    const [currentBoard, setCurrentBoard] = useState(null);
    const [boardName, setBoardName] = useState('');

    const handleCreateBoard = async () => {
        try {
            const newBoard = await createBoard({ name: boardName });
            setBoardName('');
            setOpenCreateModal(false);
            onBoardCreate(newBoard);
        } catch (error) {
            console.error('Failed to create board:', error);
        }
    };

    const handleEditBoard = async () => {
        try {
            const updatedBoard = await updateBoard(currentBoard.id, { name: boardName });
            setBoardName('');
            setCurrentBoard(null);
            setOpenEditModal(false);
            onBoardUpdate(updatedBoard);
        } catch (error) {
            console.error('Failed to update board:', error);
        }
    };

    const openEditModalWithBoard = (board) => {
        setCurrentBoard(board);
        setBoardName(board.name);
        setOpenEditModal(true);
    };

    return (
        <Box sx={{ display: 'flex', justifyContent: 'space-between' }}>
            {user && (
                <Box sx={{ width: 200, bgcolor: 'gray', p: 2 }}>
                    <Typography variant="h6">보드 목록</Typography>
                    {Array.isArray(boards) && boards.map((board) => (
                        <Box key={board.id} display="flex" alignItems="center" justifyContent="space-between">
                            <Typography>{board.name}</Typography>
                            <IconButton onClick={() => openEditModalWithBoard(board)}>
                                <MoreVertIcon />
                            </IconButton>
                        </Box>
                    ))}
                    <Box mt={2}>
                        <Button
                            startIcon={<AddIcon />}
                            onClick={() => setOpenCreateModal(true)}
                        >
                            보드 생성
                        </Button>
                    </Box>

                    {/* 보드 생성 모달 */}
                    <Modal open={openCreateModal} onClose={() => setOpenCreateModal(false)}>
                        <Box sx={{ ...modalStyle }}>
                            <Typography variant="h6">보드 생성</Typography>
                            <TextField
                                value={boardName}
                                onChange={(e) => setBoardName(e.target.value)}
                                label="보드 이름"
                                fullWidth
                                margin="normal"
                            />
                            <Button onClick={handleCreateBoard} variant="contained" color="primary">
                                생성
                            </Button>
                        </Box>
                    </Modal>

                    {/* 보드 수정 모달 */}
                    <Modal open={openEditModal} onClose={() => setOpenEditModal(false)}>
                        <Box sx={{ ...modalStyle }}>
                            <Typography variant="h6">보드 수정</Typography>
                            <TextField
                                value={boardName}
                                onChange={(e) => setBoardName(e.target.value)}
                                label="보드 이름"
                                fullWidth
                                margin="normal"
                            />
                            <Button onClick={handleEditBoard} variant="contained" color="primary">
                                수정
                            </Button>
                        </Box>
                    </Modal>
                </Box>
            )}
        </Box>
    );
};

const modalStyle = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

export default BoardList;