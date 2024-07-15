import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || '';

const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export const createBoard = async (data) => {
    try {
        const response = await api.post('/boards', data);
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const fetchBoards = async () => {
    try {
        const response = await api.get('/boards');
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};

export const updateBoard = async (id, data) => {
    try {
        const response = await api.put(`/boards/${id}`, data);
        return response.data;
    } catch (error) {
        throw error.response.data;
    }
};