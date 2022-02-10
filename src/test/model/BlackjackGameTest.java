package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    private BlackjackGame game;
    private Player player;

    @BeforeEach
    public void setUp() {
        game = new BlackjackGame();
    }

    @Test
    public void testConstructor() {
        assertEquals(1000, game.getPlayer().getBalance());
        assertEquals(0, game.getHandInString(game.getPlayerHand()).size());
        assertEquals(0, game.getHandInString(game.getDealerHand()).size());
    }

    @Test
    public void testStartGame() {
        game.startGame();
        assertEquals(2, game.getHandInString(game.getPlayerHand()).size());
        assertEquals(2, game.getHandInString(game.getDealerHand()).size());
    }

    @Test
    public void testGetDealerFirstCard() {
        game.startGame();
        assertEquals(1, game.getDealerFirstCard().size());
    }

    @Test
    public void testGetHandInString() {
        assertEquals(game.getDealerHand().size(), game.getHandInString(game.getDealerHand()).size());
        assertEquals(game.getPlayerHand().size(), game.getHandInString(game.getPlayerHand()).size());
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
    public void testGetHandInValueWithTwoAce() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(1));
        hand.add(new Card(5));
        hand.add(new Card(1));
        assertEquals(17, game.getHandInValue(hand));
    }

    @Test
    public void testCheckBlackjack() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(10));
        hand.add(new Card( 1));
        assertTrue(game.checkBlackjack(hand));
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
        assertEquals(1, game.getHandInString(hand).size());

        game.hit(hand);
        assertEquals(2, game.getHandInString(hand).size());
    }

    @Test
    public void testUpdatePlayerBalanceBlackjackPush() {
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
    public void testUpdatePlayerBalanceDealerBlackjack() {
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
    public void testUpdatePlayerBalancePlayerBlackjack() {
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
    public void testUpdatePlayerBalanceDealerBust() {
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
    public void testUpdatePlayerBalancePlayerBust() {
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
    public void testUpdatePlayerBalanceDealerWin() {
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
    public void testUpdatePlayerBalancePlayerWin() {
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
    public void testUpdatePlayerBalancePush() {
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
    public void testClearHand() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(10));
        game.clearHand(hand);
        assertEquals(0, game.getHandInString(hand).size());
    }
}