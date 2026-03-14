package com.stabgan.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int playerScore = 0;
    private int sessionScore = 0;
    private int computerScore = 0;
    private int turnScore = 0;
    private int playerBanked = 0;
    private int computerBanked = 0;
    private int computerContinue = 0;

    private final Random random = new Random();

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable computerRunnable = new Runnable() {
        @Override
        public void run() {
            ImageView diceImage = findViewById(R.id.imageView);
            TextView label = findViewById(R.id.score);

            int r = random.nextInt(6);
            if (r == 0) {
                // Computer rolled a 1 — lose turn score, revert to banked
                diceImage.setImageResource(R.drawable.dice1);
                computerScore = computerBanked;
                turnScore = 0;
                label.setText("Your score: " + playerScore + " | Computer: " + computerScore + " | Computer turn: " + turnScore);
                checkScore();
                handler.removeCallbacks(this);
                computerContinue = 1;
            } else {
                int diceValue = r + 1;
                setDiceImage(diceImage, diceValue);
                computerScore += diceValue;
                turnScore += diceValue;
                label.setText("Your score: " + playerScore + " | Computer: " + computerBanked + " | Computer turn: " + turnScore);
                computerContinue = 0;

                // Simple AI: if ahead of player, 50% chance to hold
                if (computerScore > playerScore) {
                    int choice = random.nextInt(2);
                    if (choice == 1) {
                        computerBanked = computerScore;
                        checkScore();
                        handler.removeCallbacks(this);
                        computerContinue = 1;
                    }
                }
            }

            if (computerContinue == 0) {
                handler.postDelayed(this, 1000);
            } else {
                computerContinue = 0;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button hold = findViewById(R.id.hold);
        hold.setEnabled(false);
    }

    private void setDiceImage(ImageView diceImage, int value) {
        switch (value) {
            case 1: diceImage.setImageResource(R.drawable.dice1); break;
            case 2: diceImage.setImageResource(R.drawable.dice2); break;
            case 3: diceImage.setImageResource(R.drawable.dice3); break;
            case 4: diceImage.setImageResource(R.drawable.dice4); break;
            case 5: diceImage.setImageResource(R.drawable.dice5); break;
            case 6: diceImage.setImageResource(R.drawable.dice6); break;
            default: diceImage.setImageResource(R.drawable.dice1); break;
        }
    }

    private void checkScore() {
        handler.removeCallbacks(computerRunnable);
        TextView label = findViewById(R.id.score);
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);

        if (playerScore > computerScore) {
            label.setText("You Won!");
        } else if (playerScore == computerScore) {
            label.setText("It's a Draw!");
        } else {
            label.setText("Computer Won!");
        }

        playerScore = 0;
        sessionScore = 0;
        computerScore = 0;
        turnScore = 0;
        playerBanked = 0;
        computerBanked = 0;
        roll.setEnabled(false);
        hold.setEnabled(false);
    }

    private void computerTurn() {
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);
        roll.setEnabled(false);
        hold.setEnabled(false);
        turnScore = 0;

        handler.removeCallbacks(computerRunnable);
        handler.postDelayed(computerRunnable, 1000);
    }

    public void reset(View view) {
        handler.removeCallbacks(computerRunnable);
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);
        roll.setEnabled(true);
        hold.setEnabled(false);
        TextView label = findViewById(R.id.score);
        playerScore = 0;
        sessionScore = 0;
        computerScore = 0;
        turnScore = 0;
        playerBanked = 0;
        computerBanked = 0;
        label.setText("Your score: 0 | Computer: 0 | Your turn: 0");
    }

    public void hold(View view) {
        handler.removeCallbacks(computerRunnable);
        TextView label = findViewById(R.id.score);
        sessionScore = 0;
        playerBanked = playerScore;
        label.setText("Your score: " + playerScore + " | Computer: " + computerScore + " | Your turn: 0");
        computerTurn();
    }

    public void rollDice(View view) {
        ImageView diceImage = findViewById(R.id.imageView);
        TextView label = findViewById(R.id.score);
        Button hold = findViewById(R.id.hold);

        int r = random.nextInt(6);
        if (r == 0) {
            // Rolled a 1 — lose turn score, revert to banked
            diceImage.setImageResource(R.drawable.dice1);
            sessionScore = 0;
            playerScore = playerBanked;
            hold.setEnabled(false);
            label.setText("Your score: " + playerScore + " | Computer: " + computerScore + " | Your turn: 0");
            computerTurn();
        } else {
            int diceValue = r + 1;
            setDiceImage(diceImage, diceValue);
            playerScore += diceValue;
            sessionScore += diceValue;
            hold.setEnabled(true);
            label.setText("Your score: " + playerBanked + " | Computer: " + computerScore + " | Your turn: " + sessionScore);
        }
    }
}
