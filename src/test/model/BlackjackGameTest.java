package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    private BlackjackGame game;

    @BeforeEach
    public void setUp() {
        game = new BlackjackGame();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, game.getHandString(game.getPlayerHand()).size());
        assertEquals(0, game.getHandString(game.getDealerHand()).size());
    }

    @Test
    public void testStartGame() {
        game.startGame();
        assertEquals(2, game.getHandString(game.getPlayerHand()).size());
        assertEquals(2, game.getHandString(game.getDealerHand()).size());
    }

    @Test
    public void testCheckDealerBlackjack() {
        game.startGame();
    }
}