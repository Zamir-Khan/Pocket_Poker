// This class is for creating Cards
package UTD.cs.edu.pocket_poker;

public class Card {
    //*********** By Zamir Khan **********
    // 10/9/23
    private String rank;
    private String suit;
    private int rank_point;

    // Constructor
    public Card(String rank, String suit, int rank_point){
        this.rank = rank;
        this.suit = suit;
        this.rank_point = rank_point;

    }

    // Getter methods
    public String getRank(){
        return rank;
    }
    public String getSuit(){
        return suit;
    }
    public int getPoint(){return rank_point;}

    @Override
    public String toString() {
        return rank + "_" + suit;
    }

    // End
    // ************************************
}