import React, { useState, useEffect } from 'react';
import { Box, Paper, Typography } from '@mui/material';
import { getCards, createCard, deleteCard, updateCardOrder } from '../api/cardApi';
import { DragDropContext, Droppable, Draggable } from 'react-beautiful-dnd';

const Card = ({ columnId }) => {
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

    const onDragEnd = async (result) => {
        if (!result.destination) return;

        const updatedCards = [...cards];
        const [movedCard] = updatedCards.splice(result.source.index, 1);
        updatedCards.splice(result.destination.index, 0, movedCard);

        setCards(updatedCards);
        await updateCardOrder(columnId, updatedCards.map((card, index) => ({ ...card, order: index })));
    };

    return (
        <Box>
            <Button onClick={handleAddCard}>Add Card</Button>
            <DragDropContext onDragEnd={onDragEnd}>
                <Droppable droppableId="cards" direction="vertical">
                    {(provided) => (
                        <Box {...provided.droppableProps} ref={provided.innerRef}>
                            {cards.map((card, index) => (
                                <Draggable key={card.id} draggableId={card.id} index={index}>
                                    {(provided) => (
                                        <Paper
                                            ref={provided.innerRef}
                                            {...provided.draggableProps}
                                            {...provided.dragHandleProps}
                                            sx={{ margin: 2, padding: 2 }}
                                        >
                                            <Typography variant="body1">{card.title}</Typography>
                                            <Button onClick={() => handleDeleteCard(card.id)}>Delete</Button>
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

export default Card;