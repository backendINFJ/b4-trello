import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const createBoard = async (boardData) => {
    try {
        const response = await axios.post(`${API_URL}/boards`, boardData, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const getBoards = async () => {
    try {
        const response = await axios.get(`${API_URL}/boards`, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const updateBoard = async (boardId, boardData) => {
    try {
        const response = await axios.patch(`${API_URL}/boards/${boardId}`, boardData, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const deleteBoard = async (boardId) => {
    try {
        const response = await axios.delete(`${API_URL}/boards/${boardId}`, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const inviteUser = async (boardId, inviteData) => {
    try {
        const response = await axios.post(`${API_URL}/boards/${boardId}/invitation`, inviteData, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};