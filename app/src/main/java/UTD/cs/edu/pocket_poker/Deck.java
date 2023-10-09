// This class represents the Deck
package UTD.cs.edu.pocket_poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    //*********** By Zamir Khan **********
    // 10/9/23
    private List<Card> cards;

    // Constructor
    public Deck(){
        cards = new ArrayList<>();
        for (String suit : new String[]{"Hearts", "Diamonds", "Clubs", "Spades"}){
            for (String rank : new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Shuffle
    public void shuffle(){
        Collections.shuffle(cards);
    }

    // Deal
    public Card dealCard(){
        if(!cards.isEmpty()){
            return cards.remove(0);
        } else {
            return null;    // Deck is empty
        }
    }
    // End
    // ************************************
}
