import React from 'react';
import { List, ListItem, ListItemText } from '@mui/material';

const BoardList = ({ boards, onSelectBoard }) => {
    return (
        <List component="nav">
            {boards.map(board => (
                <ListItem button key={board.id} onClick={() => onSelectBoard(board.id)}>
                    <ListItemText primary={board.title} />
                </ListItem>
            ))}
        </List>
    );
};

export default BoardList;