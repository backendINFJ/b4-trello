import axios from 'axios';

const API_URL = 'http://localhost:8080/';

export const getColumns = async (boardId) => {
    const response = await axios.get(`${API_URL}/boards/${boardId}/columns`);
    return response.data;
};

export const createColumn = async (boardId, column) => {
    const response = await axios.post(`${API_URL}/boards/${boardId}/columns`, column);
    return response.data;
};

export const deleteColumn = async (boardId, columnId) => {
    await axios.delete(`${API_URL}/boards/${boardId}/columns/${columnId}`);
};

export const updateColumnOrder = async (boardId, columns) => {
    await axios.put(`${API_URL}/boards/${boardId}/columns/order`, { columns });
};