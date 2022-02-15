package persistence;

import model.BlackjackGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/invalidFile.json");
        try {
            BlackjackGame game = reader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGame.json");
        try {
            BlackjackGame game = reader.read();
            assertEquals(1000, game.getPlayer().getBalance());
            assertEquals(0, game.getPlayer().getBet());
            assertEquals(0, game.getHandInString(game.getPlayerHand()).size());
            assertEquals(0, game.getHandInString(game.getDealerHand()).size());
        } catch (IOException e) {
            fail("Unable to read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            BlackjackGame game = reader.read();
            assertEquals(900, game.getPlayer().getBalance());
            assertEquals(100, game.getPlayer().getBet());
            assertEquals(21, game.getHandInValue(game.getPlayerHand()));
            assertEquals(5, game.getHandInValue(game.getDealerHand()));
            assertEquals(2, game.getHandInString(game.getPlayerHand()).size());
            assertEquals(2, game.getHandInString(game.getDealerHand()).size());
        } catch (IOException e) {
            fail("Unable to read from file");
        }
    }
}
