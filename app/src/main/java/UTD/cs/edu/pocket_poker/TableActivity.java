package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import utd.cs.edu.pokect_poker.R;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        // **** Creating necessary class instances for gameplay
        Deck deck = new Deck();
        deck.shuffle();
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
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
        P1Name.setText(player1.getName());
        P2Name.setText(player2.getName());

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
        // (currently serving as debugger for Player 1)
        callButton.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if (count < 5){                         // Checking if dealing card is valid
                    Card dealtCard = deck.dealCard();
                    player1.addCard(dealtCard);         // Adding card to Players hand

                    test.setText(dealtCard.toString());
                    String cardname = dealtCard.toString();
                    int resourceID = getResources().getIdentifier(cardname, "drawable", getPackageName());

                    if(resourceID !=0){
                        switch (count){
                            case 0:
                                P1card[0] = getResources().getDrawable(resourceID);
                                p1c1.setImageDrawable(P1card[0]);
                                break;
                            case 1:
                                P1card[1] = getResources().getDrawable(resourceID);
                                p1c2.setImageDrawable(P1card[1]);
                                break;
                            case 2:
                                P1card[2] = getResources().getDrawable(resourceID);
                                p1c3.setImageDrawable(P1card[2]);
                                break;
                            case 3:
                                P1card[3] = getResources().getDrawable(resourceID);
                                p1c4.setImageDrawable(P1card[3]);
                                break;
                            case 4:
                                P1card[4] = getResources().getDrawable(resourceID);
                                p1c5.setImageDrawable(P1card[4]);
                                break;
                        }
                    }
                    else{
                        test.setText("drawable not found!");
                    }
                    count++;

                }
                P1Hand.setText(player1.toString());     // Displaying Player hand

            }
        });

        // **** Action for Raise button
        // (currently serving as debugger for Player 2)
        raiseButton.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if (count < 5){
                    Card dealtCard = deck.dealCard();
                    player2.addCard(dealtCard);         // Adding card to Players hand

                    test.setText(dealtCard.toString());
                    String cardname = dealtCard.toString();
                    int resourceID = getResources().getIdentifier(cardname, "drawable", getPackageName());

                    if(resourceID !=0){
                        switch (count){
                            case 0:
                                P2card[0] = getResources().getDrawable(resourceID);
                                p2c1.setImageDrawable(P2card[0]);
                                break;
                            case 1:
                                P2card[1] = getResources().getDrawable(resourceID);
                                p2c2.setImageDrawable(P2card[1]);
                                break;
                            case 2:
                                P2card[2] = getResources().getDrawable(resourceID);
                                p2c3.setImageDrawable(P2card[2]);
                                break;
                            case 3:
                                P2card[3] = getResources().getDrawable(resourceID);
                                p2c4.setImageDrawable(P2card[3]);
                                break;
                            case 4:
                                P2card[4] = getResources().getDrawable(resourceID);
                                p2c5.setImageDrawable(P2card[4]);
                                break;
                        }
                    }
                    else{
                        test.setText("drawable not found!");
                    }
                    count++;
                }
                P2Hand.setText(player2.toString());
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
            }
        });
    }
}