import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

// 기존 함수들 주석 처리
// export const getBoards = async () => {
//     const response = await axios.get(`${API_URL}/boards`);
//     return response.data;
// };

export const getBoards = async () => {
    return [
        { id: '1', title: 'Sample Board 1', description: 'Description of Sample Board 1' },
        { id: '2', title: 'Sample Board 2', description: 'Description of Sample Board 2' }
    ];
};

export const createBoard = async (board) => {
    // 실제 API 호출은 주석 처리된 상태
    // const response = await axios.post(`${API_URL}/boards`, board);
    // return response.data;

    // 예제용 데이터 반환
    return { id: Math.random().toString(), ...board };
};

export const deleteBoard = async (boardId) => {
    // 실제 API 호출은 주석 처리된 상태
    // await axios.delete(`${API_URL}/boards/${boardId}`);

    // 예제용 성공 반환
    return true;
};

export const updateBoardName = async (boardId, newName) => {
    // 실제 API 호출은 주석 처리된 상태
    // const response = await axios.put(`${API_URL}/boards/${boardId}`, { title: newName });
    // return response.data;

    // 예제용 성공 반환
    return { id: boardId, title: newName };
};

// 다른 API 함수들도 동일하게 처리