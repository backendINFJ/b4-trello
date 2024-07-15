import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

export const login = async (credentials) => {
  const response = await axios.post(`${API_URL}/login`, credentials);
  return response.data;
};

export const reissueToken = async () => {
  const response = await axios.post(`${API_URL}/reissue`);
  return response.data;
};

export const logout = async () => {
  const response = await axios.post(`${API_URL}/logout`);
  return response.data;
};

export const sendMail = async () => {
  const response = await axios.post(`${API_URL}/send-mail`);
  return response.data;
};

export const checkMail = async (key) => {
  const response = await axios.post(`${API_URL}/check-mail`, { key });
  return response.data;
};