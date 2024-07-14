import React, { useState } from 'react';
import { List, ListItem, ListItemText, IconButton, Menu, MenuItem } from '@mui/material';
import MoreVertIcon from '@mui/icons-material/MoreVert';

const BoardList = ({ boards, onSelectBoard, onEditBoard, onDeleteBoard }) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const [selectedBoard, setSelectedBoard] = useState(null);

    const handleClick = (event, board) => {
        setAnchorEl(event.currentTarget);
        setSelectedBoard(board);
    };

    const handleClose = () => {
        setAnchorEl(null);
        setSelectedBoard(null);
    };

    return (
        <List component="nav">
            {boards.map(board => (
                <ListItem button key={board.id} onClick={() => onSelectBoard(board.id)}>
                    <ListItemText primary={board.title} />
                    <IconButton onClick={(e) => handleClick(e, board)}>
                        <MoreVertIcon />
                    </IconButton>
                </ListItem>
            ))}
            <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleClose}
            >
                <MenuItem onClick={() => { onEditBoard(selectedBoard); handleClose(); }}>Edit Board</MenuItem>
                <MenuItem onClick={() => { onDeleteBoard(selectedBoard.id); handleClose(); }}>Delete Board</MenuItem>
            </Menu>
        </List>
    );
};

export default BoardList;