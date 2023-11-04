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
        for (String suit : new String[]{"hearts", "diamonds", "clubs", "spades"}){
            int i = 1;
            for (String rank : new String[]{"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"}) {
                cards.add(new Card(rank, suit, i));
                i++;
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