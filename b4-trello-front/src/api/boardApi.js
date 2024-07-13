import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

// 기존 함수들 주석 처리
// export const getBoards = async () => {
//     const response = await axios.get(`${API_URL}/boards`);
//     return response.data;
// };

export const getBoards = async () => {
    return [
        { id: '1', title: 'Sample Board 1' },
        { id: '2', title: 'Sample Board 2' }
    ];
};

// 다른 API 함수들도 동일하게 처리