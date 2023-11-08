package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utd.cs.edu.pokect_poker.R;

public class TableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Button exitButton = findViewById(R.id.ExitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish(); // This will exit the app
            }

        });

        // **** Creating necessary class instances for gameplay
        Deck deck = new Deck();
        deck.shuffle();

        List<Player> players = new ArrayList<>();
        for(int i = 1; i < 3; i++){
            String str = Integer.toString(i);
            players.add(new Player("Player_" + str));
        }

        // ****************************************************

        // **** View instances to manipulate gameplay
        Button callButton = findViewById(R.id.callBtn);
        Button raiseButton = findViewById(R.id.raiseBtn);
        Button foldButton = findViewById(R.id.foldBtn);

        TextView P1Hand = findViewById(R.id.P1Hand);    // Display player 1 hand
        TextView P2Hand = findViewById(R.id.P2Hand);    // Display player 2 hand
        TextView test = findViewById(R.id.Test);
        TextView P1Name = findViewById(R.id.PlayerName1);
        TextView P2Name = findViewById(R.id.PlayerName2);
        P1Name.setText(players.get(0).getName());
        P2Name.setText(players.get(1).getName());

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

        Drawable[] P1card = new Drawable[5];
        Drawable[] P2card = new Drawable[5];

        // **** Action for Call button
        // (currently serving as debugger for Players)
        callButton.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                for (count=0;count<5;count++) {
                    Card dealtCard1 = deck.dealCard();
                    Card dealtCard2 = deck.dealCard();

                    players.get(0).addCard(dealtCard1);
                    players.get(1).addCard(dealtCard2);

                    String cardname1 = dealtCard1.toString();
                    int resourceID1 = getResources().getIdentifier(cardname1, "drawable", getPackageName());

                    String cardname2 = dealtCard2.toString();
                    int resourceID2 = getResources().getIdentifier(cardname2, "drawable", getPackageName());

                    if (resourceID1 != 0 && resourceID2 != 0) {
                        switch (count) {
                            case 0:
                                P1card[0] = getResources().getDrawable(resourceID1);
                                P2card[0] = getResources().getDrawable(resourceID2);
                                p1c1.setImageDrawable(P1card[0]);
                                p2c1.setImageDrawable(P2card[0]);
                                break;
                            case 1:
                                P1card[1] = getResources().getDrawable(resourceID1);
                                P2card[1] = getResources().getDrawable(resourceID2);
                                p1c2.setImageDrawable(P1card[1]);
                                p2c2.setImageDrawable(P2card[1]);
                                break;
                            case 2:
                                P1card[2] = getResources().getDrawable(resourceID1);
                                P2card[2] = getResources().getDrawable(resourceID2);
                                p1c3.setImageDrawable(P1card[2]);
                                p2c3.setImageDrawable(P2card[2]);
                                break;
                            case 3:
                                P1card[3] = getResources().getDrawable(resourceID1);
                                P2card[3] = getResources().getDrawable(resourceID2);
                                p1c4.setImageDrawable(P1card[3]);
                                p2c4.setImageDrawable(P2card[3]);
                                break;
                            case 4:
                                P1card[4] = getResources().getDrawable(resourceID1);
                                P2card[4] = getResources().getDrawable(resourceID2);
                                p1c5.setImageDrawable(P1card[4]);
                                p2c5.setImageDrawable(P2card[4]);
                                break;
                        }
                    }
                }

                /*if (count < 5){                         // Checking if dealing card is valid
                    Card dealtCard1 = deck.dealCard();
                    Card dealtCard2 = deck.dealCard();

                    players.get(0).addCard(dealtCard1);        // Adding card to Player1 hand
                    players.get(1).addCard(dealtCard2);        // Adding card to Player2 hand

                    //test.setText(dealtCard1.toString());

                    String cardname1 = dealtCard1.toString();
                    int resourceID1 = getResources().getIdentifier(cardname1, "drawable", getPackageName());

                    String cardname2 = dealtCard2.toString();
                    int resourceID2 = getResources().getIdentifier(cardname2, "drawable", getPackageName());

                    if(resourceID1 !=0 && resourceID2 != 0){
                        switch (count){
                            case 0:
                                P1card[0] = getResources().getDrawable(resourceID1);
                                P2card[0] = getResources().getDrawable(resourceID2);
                                p1c1.setImageDrawable(P1card[0]);
                                p2c1.setImageDrawable(P2card[0]);
                                break;
                            case 1:
                                P1card[1] = getResources().getDrawable(resourceID1);
                                P2card[1] = getResources().getDrawable(resourceID2);
                                p1c2.setImageDrawable(P1card[1]);
                                p2c2.setImageDrawable(P2card[1]);
                                break;
                            case 2:
                                P1card[2] = getResources().getDrawable(resourceID1);
                                P2card[2] = getResources().getDrawable(resourceID2);
                                p1c3.setImageDrawable(P1card[2]);
                                p2c3.setImageDrawable(P2card[2]);
                                break;
                            case 3:
                                P1card[3] = getResources().getDrawable(resourceID1);
                                P2card[3] = getResources().getDrawable(resourceID2);
                                p1c4.setImageDrawable(P1card[3]);
                                p2c4.setImageDrawable(P2card[3]);
                                break;
                            case 4:
                                P1card[4] = getResources().getDrawable(resourceID1);
                                P2card[4] = getResources().getDrawable(resourceID2);
                                p1c5.setImageDrawable(P1card[4]);
                                p2c5.setImageDrawable(P2card[4]);
                                break;
                        }
                    }
                    else{
                        test.setText("drawable not found!");
                    }
                    count++;

                }*/
                P1Hand.setText(players.get(0).toString());     // Displaying Player hand
                P2Hand.setText(players.get(1).toString());

                List<Player> winners = getWinner(players);
                StringBuilder strBuild = new StringBuilder("Winners: \n");
                for(int i = 0; i < winners.size(); i++){
                    strBuild.append(winners.get(i).getName())
                            .append(", Hand: ")
                            .append(getRankNames(getResult(winners.get(i))))
                            .append("\n");
                }
                test.setText(strBuild.toString());
            }
        });


        // **** Action for Raise button
        // (currently serving as debugger for Player 2)
        raiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        // **** Action for Fold button
        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                P1Hand.setText("");
                P2Hand.setText("");
                test.setText("Fold Action");

                p1c1.setImageDrawable(getDrawable(R.drawable.card_back));
                p1c2.setImageDrawable(getDrawable(R.drawable.card_back));
                p1c3.setImageDrawable(getDrawable(R.drawable.card_back));
                p1c4.setImageDrawable(getDrawable(R.drawable.card_back));
                p1c5.setImageDrawable(getDrawable(R.drawable.card_back));

                p2c1.setImageDrawable(getDrawable(R.drawable.card_back));
                p2c2.setImageDrawable(getDrawable(R.drawable.card_back));
                p2c3.setImageDrawable(getDrawable(R.drawable.card_back));
                p2c4.setImageDrawable(getDrawable(R.drawable.card_back));
                p2c5.setImageDrawable(getDrawable(R.drawable.card_back));
            }
        });
    }

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

    //Determines winner based on their hand
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

    //Breaking ties with highest card
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