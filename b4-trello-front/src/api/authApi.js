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