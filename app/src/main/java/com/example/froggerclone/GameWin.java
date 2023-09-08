package com.example.froggerclone;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class GameWin extends AppCompatActivity {
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_win_screen);

        // Get score passed from the GameScreen.
        Bundle b = getIntent().getExtras();
        int highest_score = b.getInt("score");

        Button restartButton = findViewById(R.id.restart);
        Button exitButton = findViewById(R.id.exitButton);

        TextView scoreDisplay = findViewById(R.id.winScoreNumber);
        scoreDisplay.setText(String.format("%d", highest_score));

        restartButton.setOnClickListener(
                v -> startActivity(new Intent(GameWin.this, InitialConfigurationScreen.class))
        );

        exitButton.setOnClickListener(
                v -> this.finishAffinity()
        );
    }
}
