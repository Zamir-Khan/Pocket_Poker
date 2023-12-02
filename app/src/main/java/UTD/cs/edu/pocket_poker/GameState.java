package UTD.cs.edu.pocket_poker;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {

    private List<Card> cards;
    private GamePhase currentPhase;

    public GameState(List<Card> cards, GamePhase currentPhase) {
        this.cards = cards;
        this.currentPhase = currentPhase;
    }

    public List<Card> getCards() {
        return cards;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }


    public String getGameStateInfo() {
        StringBuilder info = new StringBuilder("Current Phase: ");
        info.append(currentPhase.toString()).append("\n");
        info.append("Cards on the table:\n");

        for (Card card : cards) {
            info.append(card.toString()).append("\n");
        }

        return info.toString();
    }
    }

    // Add any other necessary methods and properties


