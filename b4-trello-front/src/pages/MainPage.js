import React, { useState, useEffect } from 'react';
import { Box, Button } from '@mui/material';
import Header from '../components/Header';
import BoardList from '../components/BoardList';
import Board from '../components/Board';
import { getBoards } from '../api/boardApi';

const MainPage = () => {
    const [boards, setBoards] = useState([]);
    const [selectedBoardId, setSelectedBoardId] = useState(null);
    const [isManager, setIsManager] = useState(false); // 매니저 권한 상태

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

    // 매니저 권한을 토글하는 함수 (테스트 용도)
    const toggleIsManager = () => {
        setIsManager(!isManager);
    };

    return (
        <Box>
            <Header />
            <Box display="flex">
                <Box width="300px">
                    <BoardList boards={boards} onSelectBoard={setSelectedBoardId} />
                    <Button onClick={toggleIsManager} sx={{ margin: 2 }}>
                        {isManager ? 'Revoke Manager' : 'Grant Manager'}
                    </Button>
                </Box>
                <Box flexGrow={1}>
                    {selectedBoardId && <Board boardId={selectedBoardId} isManager={isManager} />}
                </Box>
            </Box>
        </Box>
    );
};

export default MainPage;
