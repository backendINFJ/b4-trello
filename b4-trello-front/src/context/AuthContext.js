// src/context/AuthContext.js
import React, { createContext, useState, useEffect } from 'react';
import { getUserInfo, login as apiLogin, createUser as apiSignUp, logout as apiLogout, reissueToken as apiReissueToken } from '../api/authApi';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const initializeUser = async () => {
      try {
        const userData = await getUserInfo();
        setUser(userData);
      } catch (error) {
        console.error('Failed to fetch user info:', error);
        localStorage.removeItem('token');
      } finally {
        setLoading(false);
      }
    };

    const token = localStorage.getItem('token');
    if (token) {
      initializeUser();
    } else {
      setLoading(false);
    }
  }, []);

  const login = async (credentials) => {
    try {
      const data = await apiLogin(credentials);
      setUser(data);
      return { success: true, data };
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '로그인 실패' };
    }
  };

  const signUp = async (credentials) => {
    try {
      await apiSignUp(credentials);
    } catch (error) {
      throw new Error(error.response?.data?.message || '회원가입 실패');
    }
  };

  const logout = async () => {
    await apiLogout();
    setUser(null);
  };

  const reissueToken = async () => {
    try {
      const newTokens = await apiReissueToken();
      localStorage.setItem('token', newTokens.accessToken);
      localStorage.setItem('refreshToken', newTokens.refreshToken);
      setUser(await getUserInfo());
      return { success: true };
    } catch (error) {
      return { success: false, message: error.response?.data?.message || '시간 연장 실패' };
    }
  };

  return (
      <AuthContext.Provider value={{ user, login, signUp, logout, reissueToken, loading }}>
        {children}
      </AuthContext.Provider>
  );
};