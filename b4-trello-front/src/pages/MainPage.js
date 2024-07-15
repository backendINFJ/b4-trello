import React, { useState, useEffect } from 'react';
import { Box, Button, Snackbar, Alert, Typography } from '@mui/material';
import Header from '../components/Header';
import BoardList from '../components/BoardList';
import Board from '../components/Board';
import { createBoard, getBoards, updateBoard, deleteBoard, inviteUser } from '../api/boardApi';

const MainPage = ({ user }) => {
    const [boards, setBoards] = useState([]);
    const [newBoardOpen, setNewBoardOpen] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [selectedBoard, setSelectedBoard] = useState(null);

    useEffect(() => {
        const fetchBoards = async () => {
            if (user) {
                try {
                    const response = await getBoards();
                    setBoards(response.data.data);
                } catch (error) {
                    setSnackbarMessage('보드를 불러오는데 실패했습니다.');
                    setSnackbarSeverity('error');
                    setSnackbarOpen(true);
                }
            } else {
                // 기본 샘플 보드 설정
                const sampleBoards = [
                    {
                        id: 'sampleBoard1',
                        boardName: 'Sample Board 1',
                        description: 'This is a sample board',
                        columns: [
                            { id: '1', title: 'Sample Column 1' },
                            { id: '2', title: 'Sample Column 2' },
                        ]
                    },
                    {
                        id: 'sampleBoard2',
                        boardName: 'Sample Board 2',
                        description: 'This is another sample board',
                        columns: [
                            { id: '3', title: 'Sample Column 3' },
                            { id: '4', title: 'Sample Column 4' },
                        ]
                    }
                ];
                setBoards(sampleBoards);
            }
        };
        fetchBoards();
    }, [user]);

    const handleCreateBoard = async (boardData) => {
        try {
            const newBoard = await createBoard(boardData);
            setBoards([...boards, newBoard.data]);
            setSnackbarMessage('보드 생성 성공!');
            setSnackbarSeverity('success');
        } catch (error) {
            setSnackbarMessage('보드 생성에 실패했습니다.');
            setSnackbarSeverity('error');
        } finally {
            setSnackbarOpen(true);
        }
    };

    const handleUpdateBoard = async (boardId, boardData) => {
        try {
            const updatedBoard = await updateBoard(boardId, boardData);
            setBoards(boards.map(board => (board.id === boardId ? updatedBoard.data : board)));
            setSnackbarMessage('보드 업데이트 성공!');
            setSnackbarSeverity('success');
        } catch (error) {
            setSnackbarMessage('보드 업데이트에 실패했습니다.');
            setSnackbarSeverity('error');
        } finally {
            setSnackbarOpen(true);
        }
    };

    const handleDeleteBoard = async (boardId) => {
        try {
            await deleteBoard(boardId);
            setBoards(boards.filter(board => board.id !== boardId));
            setSnackbarMessage('보드 삭제 성공!');
            setSnackbarSeverity('success');
        } catch (error) {
            setSnackbarMessage('보드 삭제에 실패했습니다.');
            setSnackbarSeverity('error');
        } finally {
            setSnackbarOpen(true);
        }
    };

    const handleInviteUser = async (boardId, inviteData) => {
        try {
            await inviteUser(boardId, inviteData);
            setSnackbarMessage('사용자 초대 성공!');
            setSnackbarSeverity('success');
        } catch (error) {
            setSnackbarMessage('사용자 초대에 실패했습니다.');
            setSnackbarSeverity('error');
        } finally {
            setSnackbarOpen(true);
        }
    };

    const handleBoardSelect = (board) => {
        setSelectedBoard(board);
    };

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    return (
        <Box>
            <Header user={user} />
            <Box sx={{ display: 'flex', height: '100vh', overflow: 'hidden' }}>
                <BoardList
                    boards={boards}
                    onBoardSelect={handleBoardSelect}
                    onBoardUpdate={handleUpdateBoard}
                    onBoardDelete={handleDeleteBoard}
                    onCreateBoard={handleCreateBoard}
                />
                <Box sx={{ flex: 1, p: 2 }}>
                    {selectedBoard ? (
                        <Board
                            key={selectedBoard.id}
                            boardId={selectedBoard.id}
                            boardTitle={selectedBoard.boardName}
                            boardDescription={selectedBoard.description}
                            columns={selectedBoard.columns || []} // 기본값 설정
                            isManager={user && user.role === 'MANAGER'}
                            onNameChange={(newName) => handleUpdateBoard(selectedBoard.id, { boardName: newName })}
                            onDelete={() => handleDeleteBoard(selectedBoard.id)}
                            onInvite={(inviteData) => handleInviteUser(selectedBoard.id, inviteData)}
                        />
                    ) : (
                        <Typography>보드를 선택해주세요.</Typography>
                    )}
                </Box>
            </Box>
            <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={handleSnackbarClose}>
                <Alert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>
        </Box>
    );
};

export default MainPage;