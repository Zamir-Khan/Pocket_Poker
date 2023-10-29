// This class represents player
package UTD.cs.edu.pocket_poker;

import java.util.ArrayList;
import java.util.List;

public class Player {
    //*********** By Zamir Khan **********
    // 10/9/23
    private List<Card> hand;
    private int currentBet;

    // Constructor
    public Player(){
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

    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hand: ");

        for (Card card : hand) {
            stringBuilder.append(card.getRank()).append(" of ").append(card.getSuit()).append(", ");
        }

        // Remove the trailing ", "
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }




    // End
    // ************************************
}
