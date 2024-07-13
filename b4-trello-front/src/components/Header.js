import React, { useState } from 'react';
import { AppBar, Toolbar, IconButton, Typography, Button, Box, Menu, MenuItem } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import LoginForm from './LoginForm';
import SignUpForm from './SignUpForm';
import InvitationForm from './InvitationForm';
import UserManagementForm from './UserManagementForm';

const Header = () => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [loginOpen, setLoginOpen] = useState(false);
    const [signUpOpen, setSignUpOpen] = useState(false);
    const [inviteOpen, setInviteOpen] = useState(false);
    const [userManageOpen, setUserManageOpen] = useState(false);

    const handleMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleLogin = (credentials) => {
        console.log('Logging in with', credentials);
        // 로그인 로직 추가
    };

    const handleSignUp = (userInfo) => {
        console.log('Signing up with', userInfo);
        // 회원가입 로직 추가
    };

    const handleInvite = (email) => {
        console.log('Inviting', email);
        // 초대 로직 추가
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
                        <IconButton edge="end" color="inherit" aria-label="menu" onClick={handleMenu}>
                            <MenuIcon />
                        </IconButton>
                        <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleClose}>
                            <MenuItem onClick={() => setLoginOpen(true)}>로그인</MenuItem>
                            <MenuItem onClick={() => setSignUpOpen(true)}>회원가입</MenuItem>
                            <MenuItem onClick={() => setInviteOpen(true)}>초대하기</MenuItem>
                            <MenuItem onClick={() => setUserManageOpen(true)}>계정관리</MenuItem>
                        </Menu>
                    </Toolbar>
                </Box>
            </AppBar>

            <LoginForm open={loginOpen} onClose={() => setLoginOpen(false)} onLogin={handleLogin} />
            <SignUpForm open={signUpOpen} onClose={() => setSignUpOpen(false)} onSignUp={handleSignUp} />
            <InvitationForm open={inviteOpen} onClose={() => setInviteOpen(false)} onInvite={handleInvite} />
            <UserManagementForm open={userManageOpen} onClose={() => setUserManageOpen(false)} />
        </div>
    );
};

export default Header;