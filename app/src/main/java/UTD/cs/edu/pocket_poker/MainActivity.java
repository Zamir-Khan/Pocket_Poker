package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import utd.cs.edu.pokect_poker.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button HostButton;

        HostButton = findViewById(R.id.HostButton);

        HostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHostNext;

                iHostNext = new Intent(MainActivity.this, HostActivity.class);
                startActivity(iHostNext);
            }
        });

        Button TableButton;

        TableButton = findViewById(R.id.TableButton);

        TableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iTableNext;

                iTableNext = new Intent(MainActivity.this, TableActivity.class);
                startActivity(iTableNext);
            }
        });

        Button WinButton;

        WinButton = findViewById(R.id.WinButton);

        WinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iWinNext;

                iWinNext = new Intent(MainActivity.this, WinActivity.class);
                startActivity(iWinNext);
            }
        });

        Button LostButton;

        LostButton = findViewById(R.id.LostButton);

        LostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iLostNext;

                iLostNext = new Intent(MainActivity.this, LostActivity.class);
                startActivity(iLostNext);
            }
        });

        Button HowToPlayButton;

        HowToPlayButton = findViewById(R.id.HowToPlayButton);

        HowToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHowToPlayNext;

                iHowToPlayNext = new Intent(MainActivity.this, HowToPlayActivity.class);
                startActivity(iHowToPlayNext);
            }
        });

        Button exitButton = findViewById(R.id.ExitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish(); // To exit the app
            }
        });


    }

 }
