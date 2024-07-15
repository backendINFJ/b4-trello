// src/api/cardApi.js

import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getCards = async (columnId) => {
  try {
    const response = await axios.get(`${API_URL}/columns/${columnId}/cards`, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      },
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const createCard = async (columnId, cardData) => {
  try {
    const response = await axios.post(`${API_URL}/columns/${columnId}/cards`, cardData, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      },
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const deleteCard = async (columnId, cardId) => {
  try {
    const response = await axios.delete(`${API_URL}/columns/${columnId}/cards/${cardId}`, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      },
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const updateCard = async (columnId, cardId, cardData) => {
  try {
    const response = await axios.patch(`${API_URL}/columns/${columnId}/cards/${cardId}`, cardData, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      },
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export const updateCardOrder = async (columnId, cards) => {
  try {
    const response = await axios.put(`${API_URL}/columns/${columnId}/cards/order`, { cards }, {
      headers: {
        'AccessToken': localStorage.getItem('accessToken'),
      },
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};