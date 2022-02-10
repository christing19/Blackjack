package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card aceCard;
    private Card normalCard;
    private Card jackCard;
    private Card queenCard;
    private Card kingCard;

    @BeforeEach
    public void setUp() {
        aceCard = new Card(1);
        normalCard = new Card(2);
        jackCard = new Card(11);
        queenCard = new Card(12);
        kingCard = new Card(13);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, aceCard.getCardRank());
        assertEquals(11, aceCard.getCardValue());
        assertTrue(aceCard.suits.contains(aceCard.getCardSuit()));

        assertEquals(2, normalCard.getCardRank());
        assertEquals(2, normalCard.getCardValue());
        assertTrue(normalCard.suits.contains(normalCard.getCardSuit()));

        assertEquals(11, jackCard.getCardRank());
        assertEquals(10, jackCard.getCardValue());
        assertTrue(jackCard.suits.contains(jackCard.getCardSuit()));

        assertEquals(12, queenCard.getCardRank());
        assertEquals(10, queenCard.getCardValue());
        assertTrue(queenCard.suits.contains(queenCard.getCardSuit()));

        assertEquals(13, kingCard.getCardRank());
        assertEquals(10, kingCard.getCardValue());
        assertTrue(kingCard.suits.contains(kingCard.getCardSuit()));
    }

    @Test
    public void testGetCardString() {
        assertEquals("A of " + aceCard.getCardSuit(), aceCard.getCardString());
        assertEquals("2 of " + normalCard.getCardSuit(), normalCard.getCardString());
        assertEquals("J of " + jackCard.getCardSuit(), jackCard.getCardString());
        assertEquals("Q of " + queenCard.getCardSuit(), queenCard.getCardString());
        assertEquals("K of " + kingCard.getCardSuit(), kingCard.getCardString());
    }

    @Test
    public void testSetAceValue() {
        assertEquals(11, aceCard.getCardValue());
        aceCard.setAceValue();
        assertEquals(1, aceCard.getCardValue());
    }
}