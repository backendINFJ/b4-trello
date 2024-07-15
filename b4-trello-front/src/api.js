import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || '';

const api = axios.create({
  baseURL: API_URL,
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

export const login = async (credentials) => {
  try {
    const response = await api.post('/auth/login', credentials, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    localStorage.setItem('token', response.data.token);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const reissueToken = async () => {
  const response = await api.post('/auth/reissue');
  localStorage.setItem('token', response.data.token);
  return response.data;
};

export const logout = async () => {
  const response = await api.post('/auth/logout');
  localStorage.removeItem('token');
  return response.data;
};

export const getUserInfo = async () => {
  const response = await api.get('/auth/user');
  return response.data;
};

export const createBoard = async (data) => {
  const response = await api.post('/boards', data);
  return response.data;
};

export const updateBoard = async (id, data) => {
  const response = await api.put(`/boards/${id}`, data);
  return response.data;
};

export default api;