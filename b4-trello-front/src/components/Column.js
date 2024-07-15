import React, { useState, useEffect } from 'react';
import { Box, Paper, Typography, IconButton, Menu, MenuItem, Modal, TextField, Button } from '@mui/material';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import AddIcon from '@mui/icons-material/Add';
import CloseIcon from '@mui/icons-material/Close';
import CardNameModal from './CardNameModal';
import DeleteCardModal from './DeleteCardModal';
import CardDetailModal from './CardDetailModal';
import PermissionDeniedModal from './PermissionDeniedModal';

const getCards = async (columnId) => {
    return [
        { id: '1', title: 'Card Name 1', content: 'Content of Card 1' },
        { id: '2', title: 'Card Name 2', content: 'Content of Card 2' }
    ];
};

const createCard = async (columnId, card) => {
    return { id: Math.random().toString(), ...card };
};

const deleteCard = async (columnId, cardId) => {
    return true;
};

const updateCardOrder = async (columnId, cards) => {
    return true;
};

const Column = ({ columnId, title, isManager, index, moveColumn, deleteColumn }) => {
    const [cards, setCards] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);
    const [cardModalOpen, setCardModalOpen] = useState(false);
    const [deleteCardModalOpen, setDeleteCardModalOpen] = useState(false);
    const [cardToDelete, setCardToDelete] = useState(null);
    const [anchorEl, setAnchorEl] = useState(null);
    const [cardDetailOpen, setCardDetailOpen] = useState(false);
    const [selectedCard, setSelectedCard] = useState(null);
    const [columnNameModalOpen, setColumnNameModalOpen] = useState(false);
    const [columnName, setColumnName] = useState(title);

    useEffect(() => {
        const fetchCards = async () => {
            const data = await getCards(columnId);
            setCards(data);
        };
        fetchCards();
    }, [columnId]);

    const handleAddCard = async (cardName) => {
        const newCard = await createCard(columnId, { title: cardName, content: '' });
        setCards([...cards, newCard]);
    };

    const handleDeleteCard = async (cardId) => {
        await deleteCard(columnId, cardId);
        setCards(cards.filter(card => card.id !== cardId));
        setDeleteCardModalOpen(false);
    };

    const moveCard = (fromIndex, toIndex) => {
        if (toIndex < 0 || toIndex >= cards.length) return;
        const updatedCards = [...cards];
        const [movedCard] = updatedCards.splice(fromIndex, 1);
        updatedCards.splice(toIndex, 0, movedCard);
        setCards(updatedCards);
    };

    const onDragEnd = (result) => {
        if (!result.destination) return;

        if (!isManager) {
            setModalOpen(true);
            return;
        }

        const updatedCards = [...cards];
        const [movedCard] = updatedCards.splice(result.source.index, 1);
        updatedCards.splice(result.destination.index, 0, movedCard);

        setCards(updatedCards);
        updateCardOrder(columnId, updatedCards.map((card, index) => ({ ...card, order: index })));
    };

    const confirmDeleteCard = (cardId) => {
        setCardToDelete(cardId);
        setDeleteCardModalOpen(true);
    };

    const handleMenuClick = (event, card) => {
        setAnchorEl(event.currentTarget);
        setSelectedCard(card);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleCardDetailOpen = (card) => {
        setSelectedCard(card);
        setCardDetailOpen(true);
    };

    const handleCardDetailClose = () => {
        setCardDetailOpen(false);
        setSelectedCard(null);
    };

    const handleColumnNameChange = () => {
        setColumnNameModalOpen(false);
    };

    return (
        <Box sx={{ margin: 2, backgroundColor: '#f4f4f4', borderRadius: 2, width: 300 }}>
            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: 2, backgroundColor: '#e0e0e0', borderTopLeftRadius: 2, borderTopRightRadius: 2 }}>
                <Typography variant="h6" sx={{ flexGrow: 1, textAlign: 'left' }}>{columnName}</Typography>
                <IconButton onClick={() => setColumnNameModalOpen(true)}><EditIcon /></IconButton>
                <IconButton onClick={() => deleteColumn(columnId)}><DeleteIcon /></IconButton>
            </Box>
            <DragDropContext onDragEnd={onDragEnd}>
                <Droppable droppableId={`droppable-${columnId}`} direction="vertical">
                    {(provided) => (
                        <Box {...provided.droppableProps} ref={provided.innerRef} sx={{ padding: 2 }}>
                            {cards.map((card, index) => (
                                <Draggable key={card.id} draggableId={card.id} index={index}>
                                    {(provided) => (
                                        <Paper
                                            ref={provided.innerRef}
                                            {...provided.draggableProps}
                                            {...provided.dragHandleProps}
                                            sx={{ margin: 1, padding: 2, backgroundColor: '#ffffff' }}
                                        >
                                            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                                <Typography variant="body1" sx={{ flexGrow: 1 }}>{card.title}</Typography>
                                                <IconButton onClick={(event) => handleMenuClick(event, card)} size="small">
                                                    <MoreVertIcon />
                                                </IconButton>
                                                <Menu
                                                    anchorEl={anchorEl}
                                                    open={Boolean(anchorEl)}
                                                    onClose={handleMenuClose}
                                                >
                                                    <MenuItem onClick={() => moveCard(index, index - 1)}>Move Up</MenuItem>
                                                    <MenuItem onClick={() => moveCard(index, index + 1)}>Move Down</MenuItem>
                                                    <MenuItem onClick={() => confirmDeleteCard(card.id)}>Delete</MenuItem>
                                                    <MenuItem onClick={() => handleCardDetailOpen(card)}>Card Details</MenuItem>
                                                </Menu>
                                            </Box>
                                        </Paper>
                                    )}
                                </Draggable>
                            ))}
                            {provided.placeholder}
                        </Box>
                    )}
                </Droppable>
            </DragDropContext>
            <Box sx={{ display: 'flex', justifyContent: 'center', padding: 2 }}>
                <IconButton onClick={() => setCardModalOpen(true)}><AddIcon /></IconButton>
            </Box>
            <PermissionDeniedModal open={modalOpen} onClose={() => setModalOpen(false)} />
            <CardNameModal open={cardModalOpen} onClose={() => setCardModalOpen(false)} onSubmit={handleAddCard} />
            <DeleteCardModal open={deleteCardModalOpen} onClose={() => setDeleteCardModalOpen(false)} onDelete={() => handleDeleteCard(cardToDelete)} />
            <CardDetailModal open={cardDetailOpen} onClose={handleCardDetailClose} card={selectedCard} onDelete={handleDeleteCard} />
            <Modal open={columnNameModalOpen} onClose={() => setColumnNameModalOpen(false)}>
                <Box sx={{ p: 4, bgcolor: 'white', borderRadius: 1, width: 300, mx: 'auto', mt: '20vh', textAlign: 'center', position: 'relative' }}>
                    <IconButton sx={{ position: 'absolute', right: 8, top: 8 }} onClick={() => setColumnNameModalOpen(false)}>
                        <CloseIcon />
                    </IconButton>
                    <Typography variant="h6">컬럼 이름 수정</Typography>
                    <TextField
                        value={columnName}
                        onChange={(e) => setColumnName(e.target.value)}
                        fullWidth
                        margin="normal"
                        label="컬럼 이름"
                    />
                    <Button variant="contained" color="primary" onClick={handleColumnNameChange} sx={{ mt: 2 }}>
                        저장
                    </Button>
                </Box>
            </Modal>
        </Box>
    );
};

export default Column;