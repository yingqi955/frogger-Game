package com.example.froggerclone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class GameScreen extends AppCompatActivity {

    private Player player;

    private void showEndingScreen(Class cls) {
        Bundle b = new Bundle();
        b.putInt("score", player.getHighest_score());
        Intent intent = new Intent(GameScreen.this, cls);
        intent.putExtras(b);
        startActivity(intent);
    }

    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        Bundle b = getIntent().getExtras();

        int difficulty = b.getInt("difficulty");
        int sprite = b.getInt("sprite");
        String name = b.getString("name");

        System.out.printf("%d, %d, %s %n", difficulty, sprite, name);

        // Update the player_sprite to display the selected drawable.
        ImageView spriteImageView = findViewById(R.id.player_sprite);
        Drawable spriteDrawable;
        switch (sprite) {
        case 2:
            spriteDrawable = getDrawable(R.drawable.character2);
            break;
        case 3:
            spriteDrawable = getDrawable(R.drawable.character3);
            break;
        default:
            spriteDrawable = getDrawable(R.drawable.character1);
        }
        spriteImageView.setImageDrawable(spriteDrawable);

        // Update the difficultyDisplay to show the selected difficulty.
        TextView difficultyDisplay = findViewById(R.id.difficulty_display);
        switch (difficulty) {
        case 2:
            difficultyDisplay.setText(R.string.difficulty_medium);
            break;
        case 3:
            difficultyDisplay.setText(R.string.difficulty_hard);
            break;
        default:
            difficultyDisplay.setText(R.string.difficulty_easy);
        }

        // Update the livesDisplay to show the lives based on difficulty.
        TextView livesDisplay = findViewById(R.id.lives_display);
        int lives = 4 - difficulty;
        livesDisplay.setText(String.format("%d", lives));

        // Create the player instance.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        player = new Player(spriteImageView, screenWidth / 2, screenHeight, lives);

        // Update the nameDisplay to show the selected name.
        TextView nameDisplay = findViewById(R.id.name_display);
        nameDisplay.setText(name);

        Drawable[] vehicleSprites = {
            getDrawable(R.drawable.vehicle_1),
            getDrawable(R.drawable.vehicle_2),
            getDrawable(R.drawable.vehicle_3),
        };
        Drawable[] logSprites = {
            getDrawable(R.drawable.log_1),
            getDrawable(R.drawable.log_2),
            getDrawable(R.drawable.log_3),
            getDrawable(R.drawable.log_4)
        };

        ImageView vSlot1 = findViewById(R.id.vehicle_slot_1);
        ImageView vSlot2 = findViewById(R.id.vehicle_slot_2);
        ImageView vSlot3 = findViewById(R.id.vehicle_slot_3);
        ImageView vSlot4 = findViewById(R.id.vehicle_slot_4);
        ImageView vSlot5 = findViewById(R.id.vehicle_slot_5);

        ImageView lSlot1 = findViewById(R.id.log_slot_1);
        ImageView lSlot2 = findViewById(R.id.log_slot_2);
        ImageView lSlot3 = findViewById(R.id.log_slot_3);
        ImageView lSlot4 = findViewById(R.id.log_slot_4);

        int slotHeight = -138;

        // Instantiate all the entities that should be part of the game.
        MovingEntity[] entities = {
            new Vehicle(vSlot1, "sm", false, vehicleSprites, 5, slotHeight),
            new Vehicle(vSlot2, "md", true, vehicleSprites, 4, slotHeight),
            new Vehicle(vSlot3, "lg", false, vehicleSprites, 3, slotHeight),
            new Vehicle(vSlot4, "lg", true, vehicleSprites, 2, slotHeight),
            new Vehicle(vSlot5, "md", false, vehicleSprites, 1, slotHeight),
            new Log(lSlot1, "default", false, logSprites, 10, slotHeight),
            new Log(lSlot2, "d_log", true, logSprites, 9, slotHeight),
            new Log(lSlot3, "g_log", false, logSprites, 8, slotHeight),
            new Log(lSlot4, "b_log", true, logSprites, 7, slotHeight),
        };

        TextView scoreDisplay = findViewById(R.id.points_display);

        CountDownTimer t = new CountDownTimer(Long.MAX_VALUE, 17) {
            public void onTick(long millisUntilFinished) {
                // MAIN GAME LOOP - called 60fps

                boolean isCollidingWithAnyLog = false;
                for (MovingEntity entity : entities) {

                    if (entity instanceof Updatable) {
                        ((Updatable) entity).update();
                    }
                    if (entity instanceof Collidable) {
                        ((Collidable) entity).handleCollisionWithPlayer(player);
                    }

                    if (entity instanceof Log && ((Log) entity).isCollidingWithPlayer(player)) {
                        isCollidingWithAnyLog = true;
                    }
                }

                scoreDisplay.setText(String.format("%d", player.getScore()));
                livesDisplay.setText(String.format("%d", player.getLives()));

                if (player.getTileType().equals("river")) {
                    if (!isCollidingWithAnyLog) {
                        player.loseLife();
                    }
                }

                if (player.getTileType().equals("goal")) {
                    this.cancel();
                    showEndingScreen(GameWin.class);
                }

                if (player.getLives() < 0) {
                    this.cancel();
                    showEndingScreen(GameOver.class);
                }
            }

            public void onFinish() {
                System.out.println("onFinish");
                start();
            }
        }.start();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        player.handleInput(keyCode);

        return super.onKeyUp(keyCode, event);
    }
}