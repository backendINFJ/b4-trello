import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

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

export const sendMail = async () => {
  const response = await axios.post(`${API_URL}/auth/send-mail`);
  return response.data;
};

export const checkMail = async (key) => {
  const response = await axios.post(`${API_URL}/auth/check-mail`, { key });
  return response.data;
};