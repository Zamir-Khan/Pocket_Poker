// This class represents player
package UTD.cs.edu.pocket_poker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //*********** By Zamir Khan **********
    // 10/9/23
    private List<Card> hand;
    private int currentBet;
    private String name;

    // Constructor
    public Player(String n){
        name = n;
        hand = new ArrayList<>();
        currentBet = 0;
    }

    // Add a card to player's hand
    public void addCard(Card card){
        hand.add(card);
    }

    // Place a bet
    public void placeBet(int bet){
        currentBet = bet;
    }

    // Get player Hand
    public List<Card> getHand() {
        return hand;
    }
    public String getName(){ return name;}
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append(name + " Hand: \n");

        for (Card card : hand) {
            stringBuilder.append(card.getRank()).append("_").append(card.getSuit()).append("\n");
        }

        // Remove the trailing ", "
        //if (stringBuilder.length() > 0) {
        //    stringBuilder.setLength(stringBuilder.length() - 2);
        //}

        return stringBuilder.toString();
    }

    // End
    // ************************************
}