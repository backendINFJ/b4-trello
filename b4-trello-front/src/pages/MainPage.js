import React, { useState, useEffect } from 'react';
import { Box, Button } from '@mui/material';
import Header from '../components/Header';
import BoardList from '../components/BoardList';
import Board from '../components/Board';
import BoardNameModal from '../components/BoardNameModal';
import NewBoardModal from '../components/NewBoardModal';
import { getBoards, createBoard, deleteBoard, updateBoardName } from '../api/boardApi';

const MainPage = () => {
    const [boards, setBoards] = useState([]);
    const [selectedBoardId, setSelectedBoardId] = useState(null);
    const [selectedBoardTitle, setSelectedBoardTitle] = useState('');
    const [selectedBoardDescription, setSelectedBoardDescription] = useState('');
    const [isManager, setIsManager] = useState(false); // 매니저 권한 상태
    const [boardNameModalOpen, setBoardNameModalOpen] = useState(false);
    const [newBoardModalOpen, setNewBoardModalOpen] = useState(false);
    const [editBoardModalOpen, setEditBoardModalOpen] = useState(false);

    useEffect(() => {
        const fetchBoards = async () => {
            try {
                const data = await getBoards();
                setBoards(data);
                if (data.length > 0) {
                    setSelectedBoardId(data[0].id);
                    setSelectedBoardTitle(data[0].title);
                    setSelectedBoardDescription(data[0].description);
                }
            } catch (error) {
                console.error("Failed to fetch boards", error);
            }
        };
        fetchBoards();
    }, []);

    // 매니저 권한을 토글하는 함수 (테스트 용도)
    const toggleIsManager = () => {
        setIsManager(!isManager);
    };

    const handleSelectBoard = (boardId) => {
        const selectedBoard = boards.find(board => board.id === boardId);
        setSelectedBoardId(boardId);
        setSelectedBoardTitle(selectedBoard ? selectedBoard.title : '');
        setSelectedBoardDescription(selectedBoard ? selectedBoard.description : '');
    };

    const handleUpdateBoardName = (newName) => {
        updateBoardName(selectedBoardId, newName); // API call to update the board name
        const updatedBoards = boards.map(board =>
            board.id === selectedBoardId ? { ...board, title: newName } : board
        );
        setBoards(updatedBoards);
        setSelectedBoardTitle(newName);
    };

    const handleCreateBoard = async (newBoard) => {
        const createdBoard = await createBoard(newBoard);
        setBoards([...boards, createdBoard]);
        setSelectedBoardId(createdBoard.id);
        setSelectedBoardTitle(createdBoard.title);
        setSelectedBoardDescription(createdBoard.description);
    };

    const handleEditBoard = (board) => {
        setSelectedBoardId(board.id);
        setSelectedBoardTitle(board.title);
        setBoardNameModalOpen(true);
    };

    const handleDeleteBoard = async (boardId) => {
        await deleteBoard(boardId);
        setBoards(boards.filter(board => board.id !== boardId));
        setSelectedBoardId(boards.length > 0 ? boards[0].id : null);
    };

    return (
        <Box>
            <Header />
            <Box display="flex" height="calc(100vh - 64px)" overflow="hidden">
                <Box width="300px" overflow="auto">
                    <BoardList
                        boards={boards}
                        onSelectBoard={handleSelectBoard}
                        onEditBoard={handleEditBoard}
                        onDeleteBoard={handleDeleteBoard}
                    />
                    <Button onClick={() => setNewBoardModalOpen(true)} sx={{ margin: 2 }}>+ Create Board</Button>
                    <Button onClick={toggleIsManager} sx={{ margin: 2 }}>
                        {isManager ? 'Revoke Manager' : 'Grant Manager'}
                    </Button>
                </Box>
                <Box flexGrow={1} overflow="auto">
                    {selectedBoardId && (
                        <Board
                            boardId={selectedBoardId}
                            boardTitle={selectedBoardTitle}
                            boardDescription={selectedBoardDescription}
                            isManager={isManager}
                            onNameChange={() => setBoardNameModalOpen(true)}
                        />
                    )}
                </Box>
            </Box>
            <BoardNameModal
                open={boardNameModalOpen}
                onClose={() => setBoardNameModalOpen(false)}
                onSubmit={handleUpdateBoardName}
            />
            <NewBoardModal
                open={newBoardModalOpen}
                onClose={() => setNewBoardModalOpen(false)}
                onSubmit={handleCreateBoard}
            />
        </Box>
    );
};

export default MainPage;