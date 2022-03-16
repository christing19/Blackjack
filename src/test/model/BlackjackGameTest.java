package model;

import java.util.ArrayList;

import exceptions.IllegalBetException;
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
        assertEquals(1000, game.getPlayer().getBalance());
        assertEquals(0, game.getPlayer().getBet());
        assertEquals(0, game.getHandInString(game.getPlayerHand()).size());
        assertEquals(0, game.getHandInString(game.getDealerHand()).size());
    }

    @Test
    public void testStartGame() {
        game.startGame();
        assertEquals(2, game.getHandInString(game.getPlayerHand()).size());
        assertTrue(game.getPlayerHand().get(0).getCardRank() <= 13);
        assertTrue(game.getPlayerHand().get(0).getCardRank() >= 1);
        assertTrue(game.getPlayerHand().get(1).getCardRank() <= 13);
        assertTrue(game.getPlayerHand().get(1).getCardRank() >= 1);

        assertEquals(2, game.getHandInString(game.getDealerHand()).size());
        assertTrue(game.getDealerHand().get(0).getCardRank() <= 13);
        assertTrue(game.getDealerHand().get(0).getCardRank() >= 1);
        assertTrue(game.getDealerHand().get(1).getCardRank() <= 13);
        assertTrue(game.getDealerHand().get(1).getCardRank() >= 1);
    }

    @Test
    public void testGetHandInString() {
        assertEquals(game.getPlayerHand().size(), game.getHandInString(game.getPlayerHand()).size());
        assertEquals(game.getDealerHand().size(), game.getHandInString(game.getDealerHand()).size());
    }

    @Test
    public void testGetHandInValueNoAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(5, "spades"));
        hand.add(new Card(11, "spades"));
        assertEquals(15, game.getHandInValue(hand));
    }

    @Test
    public void testGetHandInValueWithOneAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1, "spades"));
        hand.add(new Card(5, "spades"));
        assertEquals(16, game.getHandInValue(hand));
    }

    @Test
    public void testGetHandInValueWithMultipleAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1, "spades"));
        hand.add(new Card(5, "spades"));
        hand.add(new Card(1, "spades"));
        assertEquals(1, 7, game.getHandInValue(hand));

        hand.add(new Card(1, "spades"));
        assertEquals(18, game.getHandInValue(hand));
    }

    @Test
    public void testGetDealerFirstCardString() {
        game.startGame();
        assertEquals(1, game.getDealerFirstCardString().size());
    }

    @Test
    public void testCheckBlackjackTrueValueTrueSize() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(10, "spades"));
        hand.add(new Card( 1, "spades"));
        assertTrue(game.checkBlackjack(hand));
        assertEquals(21, game.getHandInValue(hand));
        assertEquals(2, game.getHandInString(hand).size());
    }

    @Test
    public void testCheckBlackjackTrueValueFalseSize() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(2, "spades"));
        hand.add(new Card( 9, "spades"));
        hand.add(new Card( 10, "spades"));
        assertFalse(game.checkBlackjack(hand));
        assertEquals(21, game.getHandInValue(hand));
        assertNotEquals(2, game.getHandInString(hand).size());
    }

    @Test
    public void testCheckBlackjackFalseValueTrueSize() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(2, "spades"));
        hand.add(new Card( 3,"spades"));
        assertFalse(game.checkBlackjack(hand));
        assertNotEquals(21, game.getHandInValue(hand));
        assertEquals(2, game.getHandInString(hand).size());
    }

    @Test
    public void testCheckBlackjackFalseValueFalseSize() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(2,"spades"));
        hand.add(new Card( 3,"spades"));
        hand.add(new Card( 4,"spades"));
        assertFalse(game.checkBlackjack(hand));
        assertNotEquals(21, game.getHandInValue(hand));
        assertNotEquals(2, game.getHandInString(hand).size());
    }

    @Test
    public void testCheckBust() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(10,"spades"));
        hand.add(new Card( 10,"spades"));
        assertFalse(game.checkBust(hand));

        hand.add(new Card( 10,"spades"));
        assertTrue(game.checkBust(hand));
    }

    @Test
    public void testHit() {
        ArrayList<Card> hand = new ArrayList<>();
        game.hit(hand);
        assertEquals(1, hand.size());
        assertTrue(hand.get(0).getCardRank() <= 13);
        assertTrue(hand.get(0).getCardRank() >= 1);

        game.hit(hand);
        assertEquals(2, hand.size());
        assertTrue(hand.get(1).getCardRank() <= 13);
        assertTrue(hand.get(1).getCardRank() >= 1);
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceBlackjackPush() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(1,"spades"));
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));

        game.addCard(game.getDealerHand(), new Card(1,"spades"));
        game.addCard(game.getDealerHand(), new Card(10,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1000, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceDealerBlackjack() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(2,"spades"));
        game.addCard(game.getPlayerHand(), new Card(3,"spades"));

        game.addCard(game.getDealerHand(), new Card(1,"spades"));
        game.addCard(game.getDealerHand(), new Card(10,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(900, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePlayerBlackjack() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(1,"spades"));
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));

        game.addCard(game.getDealerHand(), new Card(2,"spades"));
        game.addCard(game.getDealerHand(), new Card(3,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1150, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePlayerBust() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));

        game.addCard(game.getDealerHand(), new Card(10,"spades"));
        game.addCard(game.getDealerHand(), new Card(10,"spades"));

        game.addCard(game.getPlayerHand(), new Card(5,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(900, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceDealerBust() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));

        game.addCard(game.getDealerHand(), new Card(10,"spades"));
        game.addCard(game.getDealerHand(), new Card(10,"spades"));
        game.addCard(game.getDealerHand(), new Card(5,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1100, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceDealerWin() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(2,"spades"));
        game.addCard(game.getPlayerHand(), new Card(3,"spades"));

        game.addCard(game.getDealerHand(), new Card(10,"spades"));
        game.addCard(game.getDealerHand(), new Card(10,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(900, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePush() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));

        game.addCard(game.getDealerHand(), new Card(10,"spades"));
        game.addCard(game.getDealerHand(), new Card(10,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1000, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePlayerWin() {
        try {
            game.getPlayer().makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));
        game.addCard(game.getPlayerHand(), new Card(10,"spades"));

        game.addCard(game.getDealerHand(), new Card(2,"spades"));
        game.addCard(game.getDealerHand(), new Card(3,"spades"));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1100, game.getPlayer().getBalance());
    }

    @Test
    public void testClearHand() {
        game.clearHand();
        assertEquals(0, game.getHandInString(game.getPlayerHand()).size());
        assertEquals(0, game.getHandInString(game.getDealerHand()).size());
    }
}