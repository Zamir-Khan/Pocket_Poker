package UTD.cs.edu.pocket_poker;

public class Main {
    public static void main(String[] args){
        // Create a deck and shuffle
        Deck deck = new Deck();
        deck.shuffle();

        // Create players
        Player player1 = new Player();
        Player player2 = new Player();

        // Deal initial cards
        for (int i=0; i<2; i++){
            player1.addCard(deck.dealCard());
            player2.addCard(deck.dealCard());
        }

        System.out.println("Player 1 Hand: " + player1.getHand());
        System.out.println("Player 2 Hand: " + player2.getHand());
    }
}
