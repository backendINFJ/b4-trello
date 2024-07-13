import React, { useState, useEffect } from 'react';
import { Box, Paper, Button, Typography } from '@mui/material';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';
// import { getCards, createCard, deleteCard, updateCardOrder } from '../api/cardApi';

// 더미 함수들 추가
const getCards = async (columnId) => {
    return [
        { id: '1', title: 'Sample Card 1' },
        { id: '2', title: 'Sample Card 2' }
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

const Column = ({ columnId }) => {
    const [cards, setCards] = useState([]);

    useEffect(() => {
        const fetchCards = async () => {
            const data = await getCards(columnId);
            setCards(data);
        };
        fetchCards();
    }, [columnId]);

    const handleAddCard = async () => {
        const newCard = await createCard(columnId, { title: 'New Card' });
        setCards([...cards, newCard]);
    };

    const handleDeleteCard = async (cardId) => {
        await deleteCard(columnId, cardId);
        setCards(cards.filter(card => card.id !== cardId));
    };

    const onDragEnd = (result) => {
        if (!result.destination) return;

        const updatedCards = [...cards];
        const [movedCard] = updatedCards.splice(result.source.index, 1);
        updatedCards.splice(result.destination.index, 0, movedCard);

        setCards(updatedCards);
        // 실제 API 호출은 주석 처리된 상태
        // await updateCardOrder(columnId, updatedCards.map((card, index) => ({ ...card, order: index })));
    };

    return (
        <Box sx={{ margin: 2, backgroundColor: '#f4f4f4', borderRadius: 2, width: 300 }}>
            <Typography variant="h6" sx={{ padding: 2, backgroundColor: '#e0e0e0', borderTopLeftRadius: 2, borderTopRightRadius: 2 }}>
                Column {columnId}
            </Typography>
            <Button onClick={handleAddCard} sx={{ margin: 2 }}>Add Card</Button>
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
                                            <Typography variant="body1">{card.title}</Typography>
                                            <Button onClick={() => handleDeleteCard(card.id)} sx={{ color: 'red' }}>Delete</Button>
                                        </Paper>
                                    )}
                                </Draggable>
                            ))}
                            {provided.placeholder}
                        </Box>
                    )}
                </Droppable>
            </DragDropContext>
        </Box>
    );
};

export default Column;