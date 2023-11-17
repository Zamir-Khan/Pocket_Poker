package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import utd.cs.edu.pokect_poker.R;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        Button acknowledgeButton = findViewById(R.id.iAcknowledgeButton);

        acknowledgeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent iPlayAgainNext;

                iPlayAgainNext = new Intent(InstructionsActivity.this, HostActivity.class);
                startActivity(iPlayAgainNext);
            }
        });

        Button exitButton = findViewById(R.id.ExitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish(); // This will exit the app
            }

        });
    }
}