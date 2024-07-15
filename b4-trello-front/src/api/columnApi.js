import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getColumns = async (boardId) => {
    try {
        const response = await axios.get(`${API_URL}/columns`, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            },
            params: { boardId }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const createColumn = async (columnData) => {
    try {
        const response = await axios.post(`${API_URL}/columns`, columnData, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const deleteColumn = async (columnId) => {
    try {
        const response = await axios.delete(`${API_URL}/columns/${columnId}`, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const updateColumnSequence = async (boardId, columnIds) => {
    try {
        const response = await axios.put(`${API_URL}/columns/sequence`, { boardId, columnIds }, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};