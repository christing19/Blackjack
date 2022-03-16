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
        aceCard = new Card(1, "Clubs");
        normalCard = new Card(2, "Diamonds");
        jackCard = new Card(11, "Hearts");
        queenCard = new Card(12, "Spades");
        kingCard = new Card(13, "Clubs");
    }

    @Test
    public void testConstructor() {
        assertEquals(1, aceCard.getCardRank());
        assertEquals(11, aceCard.getCardValue());
        assertEquals("Clubs", aceCard.getCardSuit());

        assertEquals(2, normalCard.getCardRank());
        assertEquals(2, normalCard.getCardValue());
        assertEquals("Diamonds", normalCard.getCardSuit());

        assertEquals(11, jackCard.getCardRank());
        assertEquals(10, jackCard.getCardValue());
        assertEquals("Hearts", jackCard.getCardSuit());

        assertEquals(12, queenCard.getCardRank());
        assertEquals(10, queenCard.getCardValue());
        assertEquals("Spades", queenCard.getCardSuit());

        assertEquals(13, kingCard.getCardRank());
        assertEquals(10, kingCard.getCardValue());
        assertEquals("Clubs", kingCard.getCardSuit());
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
    public void testGetCardSuitNum() {
        assertEquals(1, aceCard.getCardSuitNum());
        assertEquals(2, normalCard.getCardSuitNum());
        assertEquals(3, jackCard.getCardSuitNum());
        assertEquals(4, queenCard.getCardSuitNum());
    }
}