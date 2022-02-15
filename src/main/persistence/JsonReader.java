package persistence;

import model.BlackjackGame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.Card;
import org.json.*;

// Represents a reader that reads BlackjackGame from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads BlackjackGame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BlackjackGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBlackjackGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses BlackjackGame from JSON object and returns it
    private BlackjackGame parseBlackjackGame(JSONObject jsonObject) {
        BlackjackGame game = new BlackjackGame();
        addPlayer(game, jsonObject);
        addPlayerHand(game, jsonObject);
        addDealerHand(game, jsonObject);
        return game;
    }

    // MODIFIES: game
    // EFFECTS: parses player from JSON object and adds them to game
    private void addPlayer(BlackjackGame game, JSONObject jsonObject) {
        int balance = jsonObject.getInt("Player Balance");
        int bet = jsonObject.getInt("Player Bet");

        game.getPlayer().setBalance(balance);
        game.getPlayer().setBet(bet);
    }

    // MODIFIES: game
    // EFFECTS: parses player's hand from JSON object and adds them to game
    private void addPlayerHand(BlackjackGame game, JSONObject jsonObject) {
        JSONArray playerHandArray = jsonObject.getJSONArray("Player Hand");

        for (Object json : playerHandArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(game, game.getPlayerHand(), nextCard);
        }
    }

    // MODIFIES: game
    // EFFECTS: parses dealer's hand from JSON object and adds them to game
    private void addDealerHand(BlackjackGame game, JSONObject jsonObject) {
        JSONArray dealerHandArray = jsonObject.getJSONArray("Dealer Hand");

        for (Object json : dealerHandArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(game, game.getDealerHand(), nextCard);
        }
    }

    // MODIFIES: game
    // EFFECTS: parses card from JSON object and adds them to game
    private void addCard(BlackjackGame game, ArrayList<Card> hand, JSONObject jsonObject) {
        String suit = jsonObject.getString("cardSuit");
        int rank = jsonObject.getInt("cardRank");
        Card card = new Card(rank, suit);
        game.addCard(hand, card);
    }
}
