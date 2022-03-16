package model;

import exceptions.IllegalBetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        this.player = new Player();
    }

    @Test
    public void testConstructor() {
        assertEquals(1000, player.getBalance());
        assertEquals(0, player.getBet());
    }

    @Test
    public void testMakeBet() {
        try {
            player.makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(100,player.getBet());
        assertEquals(900,player.getBalance());

        try {
            player.makeBet(500);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(500,player.getBet());
        assertEquals(400,player.getBalance());
    }

    @Test
    public void testMakeBetExceptionLessThanZero() {
        try {
            player.makeBet(-1);
            fail("IllegalBetException was expected");
        } catch (IllegalBetException e) {
            // expected
        }
    }

    @Test
    public void testMakeBetExceptionGreaterThanBalance() {
        try {
            player.makeBet(1100);
            fail("IllegalBetException was expected");
        } catch (IllegalBetException e) {
            // expected
        }
    }

    @Test
    public void testDoubleDownBetLessThanBalance() {
        try {
            player.makeBet(100);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        assertTrue(player.doubleDown());
        assertEquals(200,player.getBet());
        assertEquals(800,player.getBalance());
    }

    @Test
    public void testDoubleDownBetEqualToBalance() {
        try {
            player.makeBet(500);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        assertTrue(player.doubleDown());
        assertEquals(1000,player.getBet());
        assertEquals(0,player.getBalance());
    }

    @Test
    public void testDoubleDownBetGreaterThanBalance() {
        try {
            player.makeBet(750);
        } catch (IllegalBetException e) {
            fail("Exception should not have been thrown");
        }
        assertFalse(player.doubleDown());
        assertEquals(750,player.getBet());
        assertEquals(250,player.getBalance());
    }

    @Test
    public void testAddBalance() {
        player.addBalance(500);
        assertEquals(1500,player.getBalance());
    }
}
