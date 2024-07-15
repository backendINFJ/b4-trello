import React, { useState, useContext, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { Box, Snackbar, Alert } from '@mui/material';
import MainPage from './pages/MainPage';
import LoginModal from './components/LoginModal';
import SignUpModal from './components/SignUpModal';
import Header from './components/Header';
import BoardList from './components/BoardList';
import { AuthProvider, AuthContext } from './context/AuthContext';

const App = () => {
  return (
      <AuthProvider>
        <Router>
          <Main />
        </Router>
      </AuthProvider>
  );
};

const Main = () => {
  const { user, login, signUp, logout, reissueToken } = useContext(AuthContext);
  const [loginOpen, setLoginOpen] = useState(false);
  const [signUpOpen, setSignUpOpen] = useState(false);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const [snackbarSeverity, setSnackbarSeverity] = useState('success');

  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  const handleLogin = async (credentials) => {
    const result = await login(credentials);
    if (result.success) {
      setSnackbarMessage('로그인 성공!');
      setSnackbarSeverity('success');
    } else {
      setSnackbarMessage(result.message);
      setSnackbarSeverity('error');
    }
    setSnackbarOpen(true);
    setLoginOpen(false);
  };

  const handleSignUp = async (userInfo) => {
    try {
      await signUp(userInfo);
      setSnackbarMessage('회원가입 성공!');
      setSnackbarSeverity('success');
      setSignUpOpen(false);
      setLoginOpen(true);
    } catch (error) {
      setSnackbarMessage(error.message || '회원가입 실패. 다시 시도해주세요.');
      setSnackbarSeverity('error');
      setSnackbarOpen(true);
    }
  };

  const handleLogout = async () => {
    await logout();
    setSnackbarMessage('로그아웃 성공!');
    setSnackbarSeverity('success');
    setSnackbarOpen(true);
  };

  const handleReissueToken = async () => {
    const result = await reissueToken();
    if (result.success) {
      setSnackbarMessage('시간 연장 성공!');
      setSnackbarSeverity('success');
    } else {
      setSnackbarMessage(result.message);
      setSnackbarSeverity('error');
    }
    setSnackbarOpen(true);
  };

  return (
      <>
        <Header />
        <Box sx={{ height: '100vh', bgcolor: 'white', paddingTop: '64px', display: 'flex' }}>
          {user && <BoardList />}
          <Box sx={{ flexGrow: 1 }}>
            <Routes>
              <Route path="/" element={<MainContent user={user} />} />
            </Routes>
          </Box>
        </Box>
        <LoginModal
            open={loginOpen}
            onClose={() => setLoginOpen(false)}
            onLogin={handleLogin} // onLogin prop 추가
            openSignUp={() => {
              setLoginOpen(false);
              setSignUpOpen(true);
            }}
        />
        <SignUpModal
            open={signUpOpen}
            onClose={() => setSignUpOpen(false)}
            onSignUp={handleSignUp}
        />
        <Snackbar
            open={snackbarOpen}
            autoHideDuration={6000}
            onClose={handleSnackbarClose}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
            sx={{ '& .MuiSnackbarContent-root': { backgroundColor: snackbarSeverity === 'success' ? 'green' : 'red', color: 'white', opacity: 0.8 } }}
        >
          <Alert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
            {snackbarMessage}
          </Alert>
        </Snackbar>
      </>
  );
};

const MainContent = ({ user }) => {
  if (!user) {
    return <Navigate to="/login" />;
  }

  return <MainPage />;
};

export default App;