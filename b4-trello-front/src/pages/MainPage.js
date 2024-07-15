// src/pages/MainPage.js
import React, { useEffect, useState, useContext } from 'react';
import { Box, Button, Typography, Snackbar, Alert } from '@mui/material';
import Header from '../components/Header';
import NewBoardModal from '../components/NewBoardModal';
import BoardList from '../components/BoardList';
import { createBoard, fetchBoards } from '../api/boardApi';
import { AuthContext } from '../context/AuthContext';

const MainPage = () => {
    const { user } = useContext(AuthContext);
    const [boards, setBoards] = useState([]);
    const [newBoardOpen, setNewBoardOpen] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');

    useEffect(() => {
        const fetchBoardsData = async () => {
            try {
                const response = await fetchBoards();
                setBoards(response.data);
            } catch (error) {
                setSnackbarMessage('보드를 불러오는 데 실패했습니다.');
                setSnackbarSeverity('error');
                setSnackbarOpen(true);
            }
        };
        fetchBoardsData();
    }, []);

    const handleCreateBoard = async (boardData) => {
        try {
            const newBoard = await createBoard(boardData);
            setBoards([...boards, newBoard]);
            setSnackbarMessage('보드 생성 성공!');
            setSnackbarSeverity('success');
        } catch (error) {
            setSnackbarMessage('보드 생성에 실패했습니다.');
            setSnackbarSeverity('error');
        } finally {
            setSnackbarOpen(true);
        }
    };

    const handleBoardUpdate = (updatedBoard) => {
        setBoards(boards.map(board => board.id === updatedBoard.id ? updatedBoard : board));
    };

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    return (
        <Box>
            <Header />
            <Box sx={{ display: 'flex', overflowX: 'auto', padding: 2 }}>
                <Box sx={{ minWidth: '300px', padding: 2, marginRight: 2, backgroundColor: '#f4f4f4', borderRadius: 1 }}>
                    <Typography variant="h6">Board List</Typography>
                    <BoardList boards={boards} onBoardCreate={handleCreateBoard} onBoardUpdate={handleBoardUpdate} />
                    {user && user.role === 'MANAGER' && (
                        <Button onClick={() => setNewBoardOpen(true)} sx={{ marginTop: 2 }}>+ Create Board</Button>
                    )}
                </Box>
                <NewBoardModal
                    open={newBoardOpen}
                    onClose={() => setNewBoardOpen(false)}
                    onSubmit={handleCreateBoard}
                />
                <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={handleSnackbarClose}>
                    <Alert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
                        {snackbarMessage}
                    </Alert>
                </Snackbar>
            </Box>
        </Box>
    );
};

export default MainPage;