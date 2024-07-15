import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

export const getColumns = async (boardId) => {
    try {
        const response = await axios.get(`${API_URL}/columns`, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            },
            params: { boardId }
        });
        if (!response.data) {
            throw new Error('Invalid response from server');
        }
        return response.data.data; // Ensure the data structure is correct
    } catch (error) {
        console.error('Error fetching columns:', error);
        throw error.response?.data || 'Error fetching columns';
    }
};

export const createColumn = async (columnData) => {
    try {
        const response = await axios.post(`${API_URL}/columns`, columnData, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        if (!response.data) {
            throw new Error('Invalid response from server');
        }
        return response.data.data;
    } catch (error) {
        console.error('Error creating column:', error);
        throw error.response?.data || 'Error creating column';
    }
};

export const deleteColumn = async (columnId) => {
    try {
        const response = await axios.delete(`${API_URL}/columns/${columnId}`, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        if (!response.data) {
            throw new Error('Invalid response from server');
        }
        return response.data.data;
    } catch (error) {
        console.error('Error deleting column:', error);
        throw error.response?.data || 'Error deleting column';
    }
};

export const updateColumnSequence = async (boardId, columnIds) => {
    try {
        const response = await axios.put(`${API_URL}/columns/sequence`, { boardId, columnIds }, {
            headers: {
                'AccessToken': localStorage.getItem('accessToken'),
            }
        });
        if (!response.data) {
            throw new Error('Invalid response from server');
        }
        return response.data.data;
    } catch (error) {
        console.error('Error updating column sequence:', error);
        throw error.response?.data || 'Error updating column sequence';
    }
};