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




        Button HowToPlayButton;

        HowToPlayButton = findViewById(R.id.HowToPlayButton);

        HowToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHowToPlayNext;

                iHowToPlayNext = new Intent(MainActivity.this, InstructionsActivity.class);
                startActivity(iHowToPlayNext);
            }
        });


    }

}