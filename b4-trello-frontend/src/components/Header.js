import React, { useState } from 'react';
import { AppBar, Toolbar, IconButton, Typography, Button, Menu, MenuItem } from '@mui/material';
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

    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" sx={{ flexGrow: 1 }}>
                        Trello
                    </Typography>
                    <IconButton edge="start" color="inherit" aria-label="menu" onClick={handleMenu}>
                        <MenuIcon />
                    </IconButton>
                    <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleClose}>
                        <MenuItem onClick={() => setLoginOpen(true)}>로그인</MenuItem>
                        <MenuItem onClick={() => setSignUpOpen(true)}>회원가입</MenuItem>
                        <MenuItem onClick={() => setInviteOpen(true)}>초대하기</MenuItem>
                        <MenuItem onClick={() => setUserManageOpen(true)}>계정관리</MenuItem>
                    </Menu>
                </Toolbar>
            </AppBar>

            <LoginForm open={loginOpen} onClose={() => setLoginOpen(false)} />
            <SignUpForm open={signUpOpen} onClose={() => setSignUpOpen(false)} />
            <InvitationForm open={inviteOpen} onClose={() => setInviteOpen(false)} />
            <UserManagementForm open={userManageOpen} onClose={() => setUserManageOpen(false)} />
        </div>
    );
};

export default Header;