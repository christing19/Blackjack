package model;

import java.util.ArrayList;

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
        hand.add(new Card(5));
        hand.add(new Card(11));
        assertEquals(15, game.getHandInValue(hand));
    }

    @Test
    public void testGetHandInValueWithOneAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(5));
        assertEquals(16, game.getHandInValue(hand));
    }

    @Test
    public void testGetHandInValueWithMultipleAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(5));
        hand.add(new Card(1));
        assertEquals(17, game.getHandInValue(hand));

        hand.add(new Card(1));
        assertEquals(18, game.getHandInValue(hand));
    }

    @Test
    public void testGetDealerFirstCardString() {
        game.startGame();
        assertEquals(1, game.getDealerFirstCardString().size());
    }

    @Test
    public void testCheckBlackjack() {
        ArrayList<Card> hand1 = new ArrayList<>();
        hand1.add(new Card(10));
        hand1.add(new Card( 1));
        assertTrue(game.checkBlackjack(hand1));
        assertEquals(21, game.getHandInValue(hand1));
        assertEquals(2, hand1.size());

        ArrayList<Card> hand2 = new ArrayList<>();
        hand2.add(new Card(2));
        hand2.add(new Card( 3));
        assertFalse(game.checkBlackjack(hand2));
        assertEquals(5, game.getHandInValue(hand2));
        assertEquals(2, hand2.size());
    }

    @Test
    public void testCheckBust() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(10));
        hand.add(new Card( 10));
        assertFalse(game.checkBust(hand));

        hand.add(new Card( 10));
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
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(1));
        game.setPlayerCard(new Card(10));

        game.setDealerCard(new Card(1));
        game.setDealerCard(new Card(10));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1000, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceDealerBlackjack() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(2));
        game.setPlayerCard(new Card(3));

        game.setDealerCard(new Card(1));
        game.setDealerCard(new Card(10));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(900, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePlayerBlackjack() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(1));
        game.setPlayerCard(new Card(10));

        game.setDealerCard(new Card(2));
        game.setDealerCard(new Card(3));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1150, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePlayerBust() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(10));
        game.setPlayerCard(new Card(10));

        game.setDealerCard(new Card(10));
        game.setDealerCard(new Card(10));

        game.setPlayerCard(new Card(5));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(900, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceDealerBust() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(10));
        game.setPlayerCard(new Card(10));

        game.setDealerCard(new Card(10));
        game.setDealerCard(new Card(10));
        game.setDealerCard(new Card(5));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1100, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalanceDealerWin() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(2));
        game.setPlayerCard(new Card(3));

        game.setDealerCard(new Card(10));
        game.setDealerCard(new Card(10));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(900, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePush() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(10));
        game.setPlayerCard(new Card(10));

        game.setDealerCard(new Card(10));
        game.setDealerCard(new Card(10));

        game.determineWinner();
        game.updatePlayerBalance();
        assertEquals(1000, game.getPlayer().getBalance());
    }

    @Test
    public void testDetermineWinnerUpdatePlayerBalancePlayerWin() {
        game.getPlayer().makeBet(100);
        game.setPlayerCard(new Card(10));
        game.setPlayerCard(new Card(10));

        game.setDealerCard(new Card(2));
        game.setDealerCard(new Card(3));

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