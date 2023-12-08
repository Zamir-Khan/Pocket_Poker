// ****** Pocket Poker ******* //
// ****** GAME ACTIVITY ****** //
package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utd.cs.edu.pokect_poker.R;

// **** By Zamir Khan and Tahmidul Karim **** //
public class TableActivity extends AppCompatActivity {
    boolean condition = false;
    final int bet = 25;
    int curBet = bet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // **** Creating necessary class instances for gameplay
        Deck deck = new Deck();
        deck.shuffle();

        List<Player> players = new ArrayList<>();
        for(int i = 1; i < 5; i++){
            String str = Integer.toString(i);
            players.add(new Player("Player " + str, bet));
        }

        // **** View instances to manipulate gameplay
        Button callButton = findViewById(R.id.callBtn);
        Button raiseButton = findViewById(R.id.raiseBtn);
        Button foldButton = findViewById(R.id.foldBtn);

        TextView test = findViewById(R.id.Test);
        TextView Ptimer = findViewById(R.id.PlayerTimer);
        TextView Stake = findViewById(R.id.TotalStake);
        TextView CurrentBet = findViewById(R.id.CurBet);

        TextView P1Name = findViewById(R.id.PlayerName1);
        TextView P2Name = findViewById(R.id.PlayerName2);
        TextView P3Name = findViewById(R.id.PlayerName3);
        TextView P4Name = findViewById(R.id.PlayerName4);
        P1Name.setText(players.get(0).getName());
        P2Name.setText(players.get(1).getName());
        P3Name.setText(players.get(2).getName());
        P4Name.setText(players.get(3).getName());

        ImageView p1c1 = findViewById(R.id.p1card1);
        ImageView p1c2 = findViewById(R.id.p1card2);
        ImageView p1c3 = findViewById(R.id.p1card3);
        ImageView p1c4 = findViewById(R.id.p1card4);
        ImageView p1c5 = findViewById(R.id.p1card5);

        ImageView p2c1 = findViewById(R.id.p2card1);
        ImageView p2c2 = findViewById(R.id.p2card2);
        ImageView p2c3 = findViewById(R.id.p2card3);
        ImageView p2c4 = findViewById(R.id.p2card4);
        ImageView p2c5 = findViewById(R.id.p2card5);

        ImageView p3c1 = findViewById(R.id.p3card1);
        ImageView p3c2 = findViewById(R.id.p3card2);
        ImageView p3c3 = findViewById(R.id.p3card3);
        ImageView p3c4 = findViewById(R.id.p3card4);
        ImageView p3c5 = findViewById(R.id.p3card5);

        ImageView p4c1 = findViewById(R.id.p4card1);
        ImageView p4c2 = findViewById(R.id.p4card2);
        ImageView p4c3 = findViewById(R.id.p4card3);
        ImageView p4c4 = findViewById(R.id.p4card4);
        ImageView p4c5 = findViewById(R.id.p4card5);

        Drawable[] P1card = new Drawable[5];
        Drawable[] P2card = new Drawable[5];
        Drawable[] P3card = new Drawable[5];
        Drawable[] P4card = new Drawable[5];

        // Timer for players
        CountDownTimer timer = new CountDownTimer(5000,1000) {
            int count = 1;
            @Override
            public void onTick(long l) {
                long secondsRemaining = l / 1000;
                Ptimer.setText("Time Remaining: "+secondsRemaining);
            }

            @Override
            public void onFinish() {

                Ptimer.setText("Time Up!");
                if (count < 6){
                    callButton.performClick();
                }
                else
                    raiseButton.performClick();
            }
        };

        // **** Action for Call button *****
        // (currently serving as debugger for Players)
        callButton.setOnClickListener(new View.OnClickListener() {
            int round = 0;
            @Override
            public void onClick(View view) {
                if (round < 5) {
                    timer.start();
                    CurrentBet.setText("Current Bet: "+ curBet);
                    Stake.setText("Total Stake: "+ (curBet * players.size()));

                    // Shuffle between each round
                    deck.shuffle();

                    Card dealtCard1 = deck.dealCard();
                    Card dealtCard2 = deck.dealCard();
                    Card dealtCard3 = deck.dealCard();
                    Card dealtCard4 = deck.dealCard();

                    players.get(0).addCard(dealtCard1);
                    players.get(1).addCard(dealtCard2);
                    players.get(2).addCard(dealtCard3);
                    players.get(3).addCard(dealtCard4);

                    String cardname1 = dealtCard1.toString();
                    int resourceID1 = getResources().getIdentifier(cardname1, "drawable", getPackageName());

                    String cardname2 = dealtCard2.toString();
                    int resourceID2 = getResources().getIdentifier(cardname2, "drawable", getPackageName());

                    String cardname3 = dealtCard3.toString();
                    int resourceID3 = getResources().getIdentifier(cardname3, "drawable", getPackageName());

                    String cardname4 = dealtCard4.toString();
                    int resourceID4 = getResources().getIdentifier(cardname4, "drawable", getPackageName());

                    // Displaying proper cards
                    if (resourceID1 != 0 && resourceID2 != 0 && resourceID3 != 0 && resourceID4 != 0) {
                        switch (round) {
                            case 0:
                                // Deal the face down card
                                P1card[0] = getResources().getDrawable(resourceID1);
                                P2card[0] = getResources().getDrawable(resourceID2);
                                P3card[0] = getResources().getDrawable(resourceID3);
                                P4card[0] = getResources().getDrawable(resourceID4);
                                p1c1.setImageDrawable(getDrawable(R.drawable.card_back));
                                p2c1.setImageDrawable(getDrawable(R.drawable.card_back));
                                p3c1.setImageDrawable(getDrawable(R.drawable.card_back));
                                p4c1.setImageDrawable(getDrawable(R.drawable.card_back));
                                // ------------------------------------------------------

                                // Deal the second card (face up)
                                deck.shuffle();
                                dealtCard1 = deck.dealCard();
                                dealtCard2 = deck.dealCard();
                                dealtCard3 = deck.dealCard();
                                dealtCard4 = deck.dealCard();
                                players.get(0).addCard(dealtCard1);
                                players.get(1).addCard(dealtCard2);
                                players.get(2).addCard(dealtCard3);
                                players.get(3).addCard(dealtCard4);

                                cardname1 = dealtCard1.toString();
                                resourceID1 = getResources().getIdentifier(cardname1, "drawable", getPackageName());
                                cardname2 = dealtCard2.toString();
                                resourceID2 = getResources().getIdentifier(cardname2, "drawable", getPackageName());
                                cardname3 = dealtCard3.toString();
                                resourceID3 = getResources().getIdentifier(cardname3, "drawable", getPackageName());
                                cardname4 = dealtCard4.toString();
                                resourceID4 = getResources().getIdentifier(cardname4, "drawable", getPackageName());

                                P1card[1] = getResources().getDrawable(resourceID1);
                                P2card[1] = getResources().getDrawable(resourceID2);
                                P3card[1] = getResources().getDrawable(resourceID3);
                                P4card[1] = getResources().getDrawable(resourceID4);
                                p1c2.setImageDrawable(P1card[1]);
                                p2c2.setImageDrawable(P2card[1]);
                                p3c2.setImageDrawable(P3card[1]);
                                p4c2.setImageDrawable(P4card[1]);

                                round++;
                                break;
                            case 2:
                                P1card[2] = getResources().getDrawable(resourceID1);
                                P2card[2] = getResources().getDrawable(resourceID2);
                                P3card[2] = getResources().getDrawable(resourceID3);
                                P4card[2] = getResources().getDrawable(resourceID4);
                                p1c3.setImageDrawable(P1card[2]);
                                p2c3.setImageDrawable(P2card[2]);
                                p3c3.setImageDrawable(P3card[2]);
                                p4c3.setImageDrawable(P4card[2]);
                                break;
                            case 3:
                                P1card[3] = getResources().getDrawable(resourceID1);
                                P2card[3] = getResources().getDrawable(resourceID2);
                                P3card[3] = getResources().getDrawable(resourceID3);
                                P4card[3] = getResources().getDrawable(resourceID4);
                                p1c4.setImageDrawable(P1card[3]);
                                p2c4.setImageDrawable(P2card[3]);
                                p3c4.setImageDrawable(P3card[3]);
                                p4c4.setImageDrawable(P4card[3]);
                                break;
                            case 4:
                                P1card[4] = getResources().getDrawable(resourceID1);
                                P2card[4] = getResources().getDrawable(resourceID2);
                                P3card[4] = getResources().getDrawable(resourceID3);
                                P4card[4] = getResources().getDrawable(resourceID4);
                                p1c5.setImageDrawable(P1card[4]);
                                p2c5.setImageDrawable(P2card[4]);
                                p3c5.setImageDrawable(P3card[4]);
                                p4c5.setImageDrawable(P4card[4]);
                                break;
                        }
                    }

                    // Deciding player turn
                    test.setText("Turn for " + players.get(playerTurn(players, round)).getName());
                    round ++;
                }
                else
                    condition = true;

            }
        });


        // **** Action for Raise button
        // (currently serving as debugger for Final Winner)
        // Press this at the end of round 5 for final result
        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(condition){
                    timer.cancel();
                    Ptimer.setText("");
                    p1c1.setImageDrawable(P1card[0]);
                    p2c1.setImageDrawable(P2card[0]);
                    p3c1.setImageDrawable(P3card[0]);
                    p4c1.setImageDrawable(P4card[0]);

                    List<Player> winners = getWinner(players);
                    StringBuilder strBuild = new StringBuilder("Winners: \n");
                    for (int i = 0; i < winners.size(); i++) {
                        strBuild.append(winners.get(i).getName())
                                .append(", Hand: ")
                                .append(getRankNames(getResult(winners.get(i))))
                                .append("\n");
                    }
                    test.setText(strBuild.toString());
                }
                else{
                    curBet += bet;
                    callButton.performClick();
                }
            }
        });

        // **** Action for Fold button ****
        // Still in development phase
        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Random fold for testing
                p3c1.setImageDrawable(getDrawable(R.drawable.card_back));
                p3c2.setImageDrawable(getDrawable(R.drawable.card_back));
                p3c3.setImageDrawable(getDrawable(R.drawable.card_back));
                p3c4.setImageDrawable(getDrawable(R.drawable.card_back));
                p3c5.setImageDrawable(getDrawable(R.drawable.card_back));
                players.remove(2);
            }
        });
    }

    // **** By Zamir Khan and Tahmidul Karim **** //
    // Game logic to determine turn
    public static int playerTurn(List<Player> players, int round){
        List<Integer> cardRanks = new ArrayList<>();
        int returnVal = 0;
        for(int i = 0; i < players.size(); i++){
            cardRanks.add(players.get(i).getHand().get(players.get(i).getHand().size() - 1).getPoint());
        }

        if(round == 1){
            int min = Collections.min(cardRanks);
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getHand().get(players.get(i).getHand().size() - 1).getPoint() == min){
                    returnVal = i;
                    break;
                }
            }
        }

        else{
            int max = Collections.max(cardRanks);
            for(int i = 0; i < players.size(); i++){
                if(players.get(i).getHand().get(players.get(i).getHand().size() - 1).getPoint() == max){
                    returnVal = i;
                    break;
                }
            }
        }
        return returnVal;
    }

    // **** By Zamir Khan and Tahmidul Karim **** //
    // Game logic to calculate card point based on hand
    public static int getResult(Player player){
        List<Card> cardList = player.getHand();

        //list of suits
        String suit_01 = cardList.get(0).getSuit();
        String suit_02 = cardList.get(1).getSuit();
        String suit_03 = cardList.get(2).getSuit();
        String suit_04 = cardList.get(3).getSuit();
        String suit_05 = cardList.get(4).getSuit();

        //list of ranks
        String rank_01 = cardList.get(0).getRank();
        String rank_02 = cardList.get(1).getRank();
        String rank_03 = cardList.get(2).getRank();
        String rank_04 = cardList.get(3).getRank();
        String rank_05 = cardList.get(4).getRank();

        //list of points
        int[] pts = new int[5];
        for(int i = 0; i < 5; i++){
            pts[i] = cardList.get(i).getPoint();
        }
        int[] sorted_pts = sort(pts);

        //checking whether all the suits are same
        if((suit_01.equals(suit_02)) && (suit_01.equals(suit_03)) && (suit_01.equals(suit_04)) && (suit_01.equals(suit_05))){
            int total_Point = 0;
            for(int i = 0; i < 5; i++){
                total_Point += cardList.get(i).getPoint();
            }

            //Royal Flush
            if(total_Point == 55){
                return 10;
            }

            //Straight Flush
            else if ((sorted_pts[4] - sorted_pts[3] == 1) && (sorted_pts[3] - sorted_pts[2] == 1) && (sorted_pts[2] - sorted_pts[1] == 1) &&
                    (sorted_pts[1] - sorted_pts[0] == 1)){
                return 9;
            }

            //Flush
            else{
                return 6;
            }
        }

        //checking for Four Of A Kind
        if(((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)) && (rank_01.equals(rank_04))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_04)) && (rank_01.equals(rank_05))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)) && (rank_01.equals(rank_05))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_04)) && (rank_02.equals(rank_05))) ||
                ((rank_01.equals(rank_05)) && (rank_01.equals(rank_03)) && (rank_01.equals(rank_04)))){
            return 8;
        }

        //checking for full house
        if(((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)) && (rank_05.equals(rank_04))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_04)) && (rank_05.equals(rank_03))) ||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_05)) && (rank_03.equals(rank_04))) ||
                ((rank_01.equals(rank_03)) && (rank_01.equals(rank_04)) && (rank_05.equals(rank_02))) ||
                ((rank_01.equals(rank_05)) && (rank_01.equals(rank_03)) && (rank_02.equals(rank_04))) ||
                ((rank_01.equals(rank_04)) && (rank_01.equals(rank_05)) && (rank_02.equals(rank_03))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_04)) && (rank_05.equals(rank_01))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_05)) && (rank_01.equals(rank_04))) ||
                ((rank_02.equals(rank_04)) && (rank_02.equals(rank_05)) && (rank_01.equals(rank_03))) ||
                ((rank_03.equals(rank_04)) && (rank_05.equals(rank_03)) && (rank_01.equals(rank_02)))){
            return 7;
        }

        //checking for simple straight
        if ((sorted_pts[4] - sorted_pts[3] == 1) && (sorted_pts[3] - sorted_pts[2] == 1) &&
                (sorted_pts[2] - sorted_pts[1] == 1) && (sorted_pts[1] - sorted_pts[0] == 1)){
            return 5;
        }

        //checking for 3-of-a-kind
        if(((rank_01.equals(rank_02)) && (rank_01.equals(rank_03)))||
                ((rank_01.equals(rank_02)) && (rank_01.equals(rank_04))) || ((rank_01.equals(rank_02)) && (rank_01.equals(rank_05))) ||
                ((rank_01.equals(rank_03)) && (rank_01.equals(rank_04))) || ((rank_01.equals(rank_05)) && (rank_01.equals(rank_03))) ||
                ((rank_01.equals(rank_04)) && (rank_01.equals(rank_05))) || ((rank_02.equals(rank_03)) && (rank_02.equals(rank_04))) ||
                ((rank_02.equals(rank_03)) && (rank_02.equals(rank_05))) || ((rank_02.equals(rank_04)) && (rank_02.equals(rank_05))) ||
                ((rank_03.equals(rank_04)) && (rank_05.equals(rank_03)))){
            return 4;
        }

        //checking for two pairs
        if(((rank_01.equals(rank_02)) && (rank_03.equals(rank_04))) || ((rank_01.equals(rank_02)) && (rank_03.equals(rank_05))) ||
                ((rank_01.equals(rank_02)) && (rank_04.equals(rank_05))) || ((rank_01.equals(rank_03)) && (rank_02.equals(rank_04))) ||
                ((rank_01.equals(rank_03)) && (rank_02.equals(rank_05))) || ((rank_01.equals(rank_03)) && (rank_04.equals(rank_05))) ||
                ((rank_01.equals(rank_04)) && (rank_02.equals(rank_03))) || ((rank_01.equals(rank_04)) && (rank_02.equals(rank_05))) ||
                ((rank_01.equals(rank_04)) && (rank_03.equals(rank_05))) || ((rank_01.equals(rank_05)) && (rank_02.equals(rank_03))) ||
                ((rank_01.equals(rank_05)) && (rank_02.equals(rank_04))) || ((rank_01.equals(rank_05)) && (rank_03.equals(rank_04))) ||
                ((rank_02.equals(rank_03)) && (rank_04.equals(rank_05))) || ((rank_02.equals(rank_04)) && (rank_03.equals(rank_05))) ||
                ((rank_02.equals(rank_05)) && (rank_03.equals(rank_04)))){
            return 3;
        }

        //checking for a pair
        if((rank_01.equals(rank_02)) || (rank_01.equals(rank_03)) || (rank_01.equals(rank_04)) || (rank_01.equals(rank_05)) ||
                (rank_02.equals(rank_03)) || (rank_02.equals(rank_04)) || (rank_02.equals(rank_05)) || (rank_03.equals(rank_04)) ||
                (rank_03.equals(rank_05)) || (rank_04.equals(rank_05))){
            return 2;
        }

        //doesn't fall into any category
        return 1;
    }

    // **** By Zamir Khan and Tahmidul Karim **** //
    // Determines winner based on their hand after last round
    public static List<Player> getWinner(List<Player> players){
        List<Integer> player_points = new ArrayList<>();

        //getting all the results
        for(int i = 0; i < players.size(); i++){
            player_points.add(getResult(players.get(i)));
        }

        //getting the winners point
        Integer max = Collections.max(player_points);

        List<Player> winners = new ArrayList<>();

        //adding whoever matches the winning point to the list
        for(int i = 0; i < players.size(); i++){
            if(player_points.get(i) == max){
                winners.add(players.get(i));
            }
        }

        //Breaking ties if all the players have same winning hand by highest card
        if(winners.size() == players.size()){
            winners.clear();

            //getting the indexes of players holding the highest card
            List<Integer> indexes = getHighestCard(players);
            for(int i = 0; i < indexes.size(); i++){
                winners.add(players.get(indexes.get(i)));
            }
        }

        return winners;
    }

    // **** By Zamir Khan and Tahmidul Karim **** //
    // Breaking ties with highest card
    public static List<Integer> getHighestCard(List<Player> players){
        //reusable arrayList to store all the card's rank points of a single player
        List<Integer> max = new ArrayList<>();

        //list to store the maximum rank number of each player
        List<Integer> plMax = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < 5; j++){
                max.add(players.get(i).getHand().get(j).getPoint());
            }
            int maximum = Collections.max(max);
            plMax.add(maximum);
            max.clear();
        }

        //listing the indexes of the players holding the highest card
        List<Integer> indexes = new ArrayList<>();
        int maximum = Collections.max(plMax);
        for(int i = 0; i < players.size(); i++){
            if(plMax.get(i) == maximum){
                indexes.add(i);
            }
        }
        return indexes;
    }

    // **** By Zamir Khan and Tahmidul Karim **** //
    //Getting specific names for the players' hands
    public static String getRankNames(int num){
        String str = "";

        switch (num){
            case 1:
                str = "High Card";
                break;
            case 2:
                str = "A Pair";
                break;
            case 3:
                str = "Two Pairs";
                break;
            case 4:
                str = "Three of A Kind";
                break;
            case 5:
                str = "Straight";
                break;
            case 6:
                str = "Flush";
                break;
            case 7:
                str = "Full House";
                break;
            case 8:
                str = "Four of A Kind";
                break;
            case 9:
                str = "Straight Flush";
                break;
            case 10:
                str = "Royal Flush";
                break;
        }
        return str;
    }

    // **** By Zamir Khan and Tahmidul Karim **** //
    // Standard insertion sort
    public static int[] sort(int arr[])
    {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
        return arr;
    }
}