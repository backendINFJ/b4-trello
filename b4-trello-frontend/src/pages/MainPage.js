import React, { useState, useEffect } from 'react';
import { Box } from '@mui/material';
import Header from '../components/Header';
import BoardList from '../components/BoardList';
import Board from '../components/Board';
import { getBoards } from '../api/boardApi';

const MainPage = () => {
    const [boards, setBoards] = useState([]);
    const [selectedBoardId, setSelectedBoardId] = useState(null);

    useEffect(() => {
        const fetchBoards = async () => {
            try {
                const data = await getBoards();
                setBoards(data);
                if (data.length > 0) {
                    setSelectedBoardId(data[0].id);
                }
            } catch (error) {
                console.error("Failed to fetch boards", error);
            }
        };
        fetchBoards();
    }, []);

    return (
        <Box>
            <Header />
            <Box display="flex">
                <Box width="300px">
                    <BoardList boards={boards} onSelectBoard={setSelectedBoardId} />
                </Box>
                <Box flexGrow={1}>
                    {selectedBoardId && <Board boardId={selectedBoardId} />}
                </Box>
            </Box>
        </Box>
    );
};

export default MainPage;