import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

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

// deleteComment 함수 추가
export const deleteComment = async (commentId) => {
  try {
    const response = await axios.delete(`${API_URL}/comments/${commentId}`, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      }
    });
    return response.data.data;
  } catch (error) {
    throw error.response.data;
  }
};