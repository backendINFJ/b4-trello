import React, { useState, useEffect } from 'react';
import { AppBar, Toolbar, IconButton, Typography, Button, Box, Menu, MenuItem, Snackbar, Alert } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import LoginForm from './LoginForm';
import SignUpForm from './SignUpForm';
import InvitationForm from './InvitationForm';
import UserManagementForm from './UserManagementForm';
import { login, reissueToken, logout } from '../api/authApi';

const Header = ({ onLogin, user: initialUser }) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [loginOpen, setLoginOpen] = useState(false);
    const [signUpOpen, setSignUpOpen] = useState(false);
    const [inviteOpen, setInviteOpen] = useState(false);
    const [userManageOpen, setUserManageOpen] = useState(false);
    const [user, setUser] = useState(initialUser);
    const [expiryTime, setExpiryTime] = useState(30);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');

    useEffect(() => {
        if (user) {
            const timer = setInterval(() => {
                setExpiryTime((prev) => prev - 1);
            }, 60000);
            return () => clearInterval(timer);
        }
    }, [user]);

    const handleMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogin = async (credentials) => {
        try {
            const userData = await login(credentials);
            setUser(userData);
            setSnackbarMessage('로그인 성공!');
            setSnackbarSeverity('success');
            setLoginOpen(false);
        } catch (error) {
            setSnackbarMessage('로그인 실패. 다시 시도해주세요.');
            setSnackbarSeverity('error');
            console.error('Failed to login:', error);
        } finally {
            setSnackbarOpen(true);
        }
    };

    const handleSignUp = async (userInfo) => {
        try {
            // SignUp 로직을 추가하세요
            setSignUpOpen(false);
        } catch (error) {
            console.error('Failed to sign up:', error);
        }
    };

    const handleInvite = async (email) => {
        try {
            // Invite 로직을 추가하세요
            setInviteOpen(false);
        } catch (error) {
            console.error('Failed to invite:', error);
        }
    };

    const handleLogout = async () => {
        try {
            await logout();
            setUser(null);
            setExpiryTime(30);
            setSnackbarMessage('로그아웃 성공!');
            setSnackbarSeverity('success');
            setSnackbarOpen(true);
        } catch (error) {
            setSnackbarMessage('로그아웃 실패. 다시 시도해주세요.');
            setSnackbarSeverity('error');
            console.error('Failed to logout:', error);
            setSnackbarOpen(true);
        }
    };

    const handleExtendTime = async () => {
        try {
            const newTokens = await reissueToken();
            console.log('New tokens:', newTokens);
            setExpiryTime(30);
            setSnackbarMessage('시간 연장 성공!');
            setSnackbarSeverity('success');
            setSnackbarOpen(true);
        } catch (error) {
            setSnackbarMessage('시간 연장 실패. 다시 시도해주세요.');
            setSnackbarSeverity('error');
            console.error('Failed to reissue token:', error);
            setSnackbarOpen(true);
        }
    };

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };

    return (
        <div>
            <AppBar position="static">
                <Box sx={{ position: 'relative', height: '45px', backgroundColor: '#3C3C3C' }}>
                    <Box sx={{ position: 'absolute', top: '5px', left: '5px', display: 'flex', gap: '5px' }}>
                        <Button sx={{ minWidth: '8px', height: '8px', backgroundColor: 'red', borderRadius: '50%' }}></Button>
                        <Button sx={{ minWidth: '8px', height: '8px', backgroundColor: 'orange', borderRadius: '50%' }}></Button>
                        <Button sx={{ minWidth: '8px', height: '8px', backgroundColor: 'green', borderRadius: '50%' }}></Button>
                    </Box>
                    <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
                        <Typography variant="h6" sx={{ flexGrow: 1, textAlign: 'center' }}>
                            Trello
                        </Typography>
                        {user ? (
                            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                                <Typography>{user.nickname}</Typography>
                                <AccessTimeIcon />
                                <Typography>{expiryTime}분</Typography>
                                <Button variant="outlined" color="inherit" onClick={handleExtendTime}>
                                    시간 연장
                                </Button>
                                <Button color="inherit" onClick={handleLogout}>
                                    로그아웃
                                </Button>
                            </Box>
                        ) : (
                            <Button color="inherit" onClick={() => setLoginOpen(true)}>
                                로그인
                            </Button>
                        )}
                        <IconButton edge="end" color="inherit" aria-label="menu" onClick={handleMenu}>
                            <MenuIcon />
                        </IconButton>
                        <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleClose}>
                            {user ? (
                                <MenuItem onClick={handleLogout}>로그아웃</MenuItem>
                            ) : (
                                <MenuItem onClick={() => setLoginOpen(true)}>로그인</MenuItem>
                            )}
                            <MenuItem onClick={() => setSignUpOpen(true)}>회원가입</MenuItem>
                            <MenuItem onClick={() => setInviteOpen(true)}>초대하기</MenuItem>
                            <MenuItem onClick={() => setUserManageOpen(true)}>계정관리</MenuItem>
                        </Menu>
                    </Toolbar>
                </Box>
            </AppBar>

            {loginOpen && (
                <div>
                    <LoginForm open={loginOpen} onClose={() => setLoginOpen(false)} onLogin={handleLogin} />
                </div>
            )}
            {signUpOpen && (
                <div>
                    <SignUpForm open={signUpOpen} onClose={() => setSignUpOpen(false)} onSignUp={handleSignUp} />
                </div>
            )}
            {inviteOpen && (
                <div>
                    <InvitationForm open={inviteOpen} onClose={() => setInviteOpen(false)} onInvite={handleInvite} />
                </div>
            )}
            {userManageOpen && (
                <div>
                    <UserManagementForm open={userManageOpen} onClose={() => setUserManageOpen(false)} user={user} />
                </div>
            )}

            <Snackbar open={snackbarOpen} autoHideDuration={6000} onClose={handleSnackbarClose}>
                <Alert onClose={handleSnackbarClose} severity={snackbarSeverity} sx={{ width: '100%' }}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>
        </div>
    );
};

export default Header;