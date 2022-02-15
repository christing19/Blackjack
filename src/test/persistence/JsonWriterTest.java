package persistence;

import model.BlackjackGame;
import model.Card;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            BlackjackGame game = new BlackjackGame();
            JsonWriter writer = new JsonWriter("./data/\0invalidFileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGame() {
        try {
            BlackjackGame game = new BlackjackGame();

            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            game = reader.read();
            assertEquals(1000, game.getPlayer().getBalance());
            assertEquals(0, game.getPlayer().getBet());
            assertEquals(0, game.getHandInString(game.getPlayerHand()).size());
            assertEquals(0, game.getHandInString(game.getDealerHand()).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            BlackjackGame game = new BlackjackGame();
            game.getPlayer().makeBet(100);
            game.addCard(game.getPlayerHand(), new Card(1,"spades"));
            game.addCard(game.getPlayerHand(), new Card(10,"spades"));
            game.addCard(game.getDealerHand(), new Card(2,"spades"));
            game.addCard(game.getDealerHand(), new Card(3,"spades"));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();
            assertEquals(900, game.getPlayer().getBalance());
            assertEquals(100, game.getPlayer().getBet());
            assertEquals(21, game.getHandInValue(game.getPlayerHand()));
            assertEquals(5, game.getHandInValue(game.getDealerHand()));
            assertEquals(2, game.getHandInString(game.getPlayerHand()).size());
            assertEquals(2, game.getHandInString(game.getDealerHand()).size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
