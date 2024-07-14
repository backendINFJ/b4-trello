import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // API URL을 실제 서버 URL로 변경하세요

export const createUser = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/users`, userData);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const updateUser = async (userId, userData) => {
  try {
    const response = await axios.patch(`${API_URL}/users/${userId}`, userData);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const deleteUser = async (userId) => {
  try {
    const response = await axios.post(`${API_URL}/users/delete/${userId}`);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};