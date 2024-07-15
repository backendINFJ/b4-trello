import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

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

export const updateBoard = async (boardId, boardData) => {
    try {
        const response = await axios.put(`${API_URL}/boards/${boardId}`, boardData, {
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
        const response = await axios.post(`${API_URL}/boards/${boardId}/invite`, inviteData, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const updateBoardName = async (boardId, newName) => {
    try {
        const response = await axios.put(`${API_URL}/boards/${boardId}/name`, { name: newName }, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};