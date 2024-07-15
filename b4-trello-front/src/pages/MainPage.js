import React, { useState, useEffect } from 'react';
import { Box, Button, Typography, Modal, Snackbar, Alert, IconButton, Menu, MenuItem } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import CloseIcon from '@mui/icons-material/Close';
import Header from '../components/Header';
import NewBoardModal from '../components/NewBoardModal';
import Board from '../components/Board';
import { createBoard, getBoards, updateBoard, deleteBoard, inviteUser } from '../api/boardApi';

const MainPage = ({ user }) => {
    const [boards, setBoards] = useState([]);
    const [newBoardOpen, setNewBoardOpen] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [menuAnchorEl, setMenuAnchorEl] = useState(null);
    const [selectedBoardId, setSelectedBoardId] = useState(null);

    useEffect(() => {
        const fetchBoards = async () => {
            try {
                const response = await getBoards();
                const data = response.data;
                setBoards(data);
            } catch (error) {
                setBoards([
                    {
                        id: 'sampleBoard1',
                        boardName: 'Sample Board 1',
                        description: 'This is a sample board',
                        columns: [
                            {
                                id: 'sampleColumn1',
                                title: 'Sample Column 1',
                                cards: [
                                    { id: 'sampleCard1', title: 'Sample Card 1', content: 'This is a sample card' },
                                    { id: 'sampleCard2', title: 'Sample Card 2', content: 'This is another sample card' },
                                ],
                            },
                            {
                                id: 'sampleColumn2',
                                title: 'Sample Column 2',
                                cards: [
                                    { id: 'sampleCard3', title: 'Sample Card 3', content: 'This is a third sample card' },
                                ],
                            },
                        ],
                    },
                ]);
                setSnackbarMessage('보드를 불러오는데 실패했습니다.');
                setSnackbarSeverity('error');
                setSnackbarOpen(true);
            }
        };
        fetchBoards();
    }, []);

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

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    const handleMenuClick = (event, boardId) => {
        setMenuAnchorEl(event.currentTarget);
        setSelectedBoardId(boardId);
    };

    const handleMenuClose = () => {
        setMenuAnchorEl(null);
        setSelectedBoardId(null);
    };

    return (
        <Box>
            <Header user={user} />
            <Box sx={{ display: 'flex', padding: 2 }}>
                <Box sx={{ minWidth: '300px', padding: 2, marginRight: 2, backgroundColor: '#f4f4f4', borderRadius: 1 }}>
                    <Typography variant="h6">Board List</Typography>
                    {boards.map((board) => (
                        <Box key={board.id} sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 2 }}>
                            <Typography>{board.boardName}</Typography>
                            <IconButton onClick={(event) => handleMenuClick(event, board.id)}>
                                <MoreVertIcon />
                            </IconButton>
                        </Box>
                    ))}
                    <Menu
                        anchorEl={menuAnchorEl}
                        open={Boolean(menuAnchorEl)}
                        onClose={handleMenuClose}
                    >
                        <MenuItem onClick={() => handleUpdateBoard(selectedBoardId, { boardName: 'New Name', description: '' })}>Edit Board</MenuItem>
                        <MenuItem onClick={() => setNewBoardOpen(true)}>Create Column</MenuItem>
                        <MenuItem onClick={() => handleDeleteBoard(selectedBoardId)}>Delete Board</MenuItem>
                    </Menu>
                    {user && user.role === 'MANAGER' && (
                        <Button onClick={() => setNewBoardOpen(true)} sx={{ marginTop: 2 }}>+ Create Board</Button>
                    )}
                </Box>
                {boards.length > 0 ? (
                    <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
                        {boards.map((board) => (
                            <Board
                                key={board.id}
                                boardId={board.id}
                                boardTitle={board.boardName}
                                boardDescription={board.description}
                                isManager={user && user.role === 'MANAGER'}
                                onNameChange={() => handleUpdateBoard(board.id, { boardName: 'New Name', description: board.description })}
                                onDelete={() => handleDeleteBoard(board.id)}
                                onInvite={(inviteData) => handleInviteUser(board.id, inviteData)}
                            />
                        ))}
                    </Box>
                ) : (
                    <Typography>보드를 불러오는 중입니다...</Typography>
                )}
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