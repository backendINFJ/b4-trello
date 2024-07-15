import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

// 유저 API 호출
export const createUser = async (userData) => {
  const response = await axios.post(`${API_URL}/users`, userData);
  return response.data;
};

export const updateUser = async (userId, userData) => {
  const response = await axios.patch(`${API_URL}/users/${userId}`, userData);
  return response.data;
};

export const deleteUser = async (userId) => {
  const response = await axios.delete(`${API_URL}/users/${userId}`);
  return response.data;
};

export const sendInvite = async (inviteData) => {
  const response = await axios.post(`${API_URL}/users/invite`, inviteData);
  return response.data;
};

// 인증 관련 API 호출
export const login = async (credentials) => {
  const response = await axios.post(`${API_URL}/auth/login`, credentials);
  return response.data;
};

export const reissueToken = async () => {
  const response = await axios.post(`${API_URL}/auth/reissue`);
  return response.data;
};

export const logout = async () => {
  const response = await axios.post(`${API_URL}/auth/logout`);
  return response.data;
};