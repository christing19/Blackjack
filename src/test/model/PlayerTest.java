package model;

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
        player.makeBet(100);
        assertEquals(100,player.getBet());
        assertEquals(900,player.getBalance());

        player.makeBet(500);
        assertEquals(500,player.getBet());
        assertEquals(400,player.getBalance());
    }

    @Test
    public void testDoubleDownBetLessThanBalance() {
        player.makeBet(100);
        assertTrue(player.doubleDown());
        assertEquals(200,player.getBet());
        assertEquals(800,player.getBalance());
    }

    @Test
    public void testDoubleDownBetEqualToBalance() {
        player.makeBet(500);
        assertTrue(player.doubleDown());
        assertEquals(1000,player.getBet());
        assertEquals(0,player.getBalance());
    }

    @Test
    public void testDoubleDownBetGreaterThanBalance() {
        player.makeBet(750);
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
