// This class is for creating Cards
package UTD.cs.edu.pocket_poker;

public class Card {
    //*********** By Zamir Khan **********
    // 10/9/23
    private String rank;
    private String suit;

    // Constructor
    public Card(String rank, String suit){
        this.rank = rank;
        this.suit = suit;
    }

    // Getter methods
    public String getRank(){
        return rank;
    }
    public String getSuit(){
        return suit;
    }

    @Override
    public String toString() {
        return rank + "_" + suit;
    }

    // End
    // ************************************
}
