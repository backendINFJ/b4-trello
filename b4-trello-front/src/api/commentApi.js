import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getComments = async (cardId) => {
  try {
    const response = await axios.get(`${API_URL}/cards/${cardId}/comments`, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      }
    });
    return response.data.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const createComment = async (cardId, content) => {
  try {
    const response = await axios.post(`${API_URL}/cards/${cardId}/comments`, { content }, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      }
    });
    return response.data.data;
  } catch (error) {
    throw error.response.data;
  }
};