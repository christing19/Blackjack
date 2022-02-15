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
        aceCard = new Card(1, "clubs");
        normalCard = new Card(2, "diamonds");
        jackCard = new Card(11, "hearts");
        queenCard = new Card(12, "spades");
        kingCard = new Card(13, "clubs");
    }

    @Test
    public void testConstructor() {
        assertEquals(1, aceCard.getCardRank());
        assertEquals(11, aceCard.getCardValue());
        assertEquals("clubs", aceCard.getCardSuit());

        assertEquals(2, normalCard.getCardRank());
        assertEquals(2, normalCard.getCardValue());
        assertEquals("diamonds", normalCard.getCardSuit());

        assertEquals(11, jackCard.getCardRank());
        assertEquals(10, jackCard.getCardValue());
        assertEquals("hearts", jackCard.getCardSuit());

        assertEquals(12, queenCard.getCardRank());
        assertEquals(10, queenCard.getCardValue());
        assertEquals("spades", queenCard.getCardSuit());

        assertEquals(13, kingCard.getCardRank());
        assertEquals(10, kingCard.getCardValue());
        assertEquals("clubs", kingCard.getCardSuit());
    }

    @Test
    public void testGetCardString() {
        assertEquals("A of " + aceCard.getCardSuit(), aceCard.getCardString());
        assertEquals("2 of " + normalCard.getCardSuit(), normalCard.getCardString());
        assertEquals("J of " + jackCard.getCardSuit(), jackCard.getCardString());
        assertEquals("Q of " + queenCard.getCardSuit(), queenCard.getCardString());
        assertEquals("K of " + kingCard.getCardSuit(), kingCard.getCardString());
    }
}