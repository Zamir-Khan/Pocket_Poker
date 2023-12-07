// This class represents player
package UTD.cs.edu.pocket_poker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //*********** By Zamir Khan **********
    // 10/9/23
    private List<Card> hand;    // Player hand
    private int currentBet;     // Player bet
    private String name;        // Player name

    // Constructor
    public Player(String n, int bet){
        name = n;
        hand = new ArrayList<>();
        currentBet = bet;
    }

    // Add a card to player's hand
    public void addCard(Card card){
        hand.add(card);
    }

    // Place a bet
    public void placeBet(int bet){
        currentBet = bet;
    }

    // Get player bet
    public int getCurrentBet(){return currentBet;}
    // Get player Hand
    public List<Card> getHand() {
        return hand;
    }

    // Get player name
    public String getName(){ return name;}
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card : hand) {
            stringBuilder.append(card.getRank()).append("_").append(card.getSuit()).append("\n");
        }
        return stringBuilder.toString();
    }
    // End
    // ************************************
}
