package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;

    @BeforeEach
    public void setUp() {
        this.card = new Card();
    }

    @Test
    public void testConstructor() {
        assertTrue(card.getCardRank() <= 13);
        assertTrue(card.getCardValue() <= 11);
        assertTrue(card.suits.contains(card.getCardSuit()));
    }

    @Test
    public void testGetCardString() {
    }
}