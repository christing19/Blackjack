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
        player.makeBet(500);
        assertEquals(500,player.getBet());
    }

    @Test
    public void testReceiveWinnings() {
    }
}
