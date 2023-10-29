package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

import utd.cs.edu.pokect_poker.R;

public class TableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Deck deck = new Deck();
        deck.shuffle();
        Player player1 = new Player();
        Player player2 = new Player();

        Button callButton = findViewById(R.id.callBtn);
        Button raiseButton = findViewById(R.id.raiseBtn);
        Button foldButton = findViewById(R.id.foldBtn);
        TextView testText = findViewById(R.id.BtnText);
        callButton.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if (count < 5){
                    player1.addCard(deck.dealCard());
                    count++;
                }
                testText.setText("Player 1 " +player1.toString());
            }
        });

        raiseButton.setOnClickListener(new View.OnClickListener() {
            int count = 0;
            @Override
            public void onClick(View view) {
                if (count < 5){
                    player2.addCard(deck.dealCard());
                    count++;
                }
                testText.setText("Player 2 " +player2.toString());
            }
        });

        foldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testText.setText("You Pressed Fold button");
            }
        });
    }
}