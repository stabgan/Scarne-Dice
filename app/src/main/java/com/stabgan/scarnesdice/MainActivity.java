package com.stabgan.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int player_score = 0;
    private int session_score = 0;
    private int computer_score = 0;
    private int turn_score = 0;
    private int temp = 0; // Stores player's score at start of turn
    private Random random = new Random();
    private int f = 0; // Flag for computer turn control


    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ImageView diceImage = findViewById(R.id.imageView);
            TextView label = findViewById(R.id.score);

            int r = random.nextInt(6) + 1; // Generate 1-6 instead of 0-5
            
            if (r == 1) {
                // Computer rolls a 1, loses turn
                diceImage.setImageResource(R.drawable.dice1);
                turn_score = 0;
                label.setText("player's score: " + player_score + " computer score: " + computer_score + " computer's turn score: " + turn_score);
                checkScore();
                handler.removeCallbacks(this);
                f = 1;
            } else {
                // Computer rolls 2-6, adds to turn score
                switch (r) {
                    case 2:
                        diceImage.setImageResource(R.drawable.dice2);
                        break;
                    case 3:
                        diceImage.setImageResource(R.drawable.dice3);
                        break;
                    case 4:
                        diceImage.setImageResource(R.drawable.dice4);
                        break;
                    case 5:
                        diceImage.setImageResource(R.drawable.dice5);
                        break;
                    case 6:
                        diceImage.setImageResource(R.drawable.dice6);
                        break;
                }
                
                turn_score += r;
                int potential_score = computer_score + turn_score;
                label.setText("player's score: " + player_score + " computer score: " + computer_score + " computer's turn score: " + turn_score);
                
                // Simple AI: stop if ahead or if turn score >= 20
                if (potential_score >= player_score || turn_score >= 20) {
                    computer_score = potential_score;
                    turn_score = 0;
                    checkScore();
                    handler.removeCallbacks(this);
                    f = 1;
                } else {
                    f = 0;
                }
            }
            
            if (f == 0) {
                handler.postDelayed(this, 1000);
            } else {
                f = 0;
            }
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button hold = findViewById(R.id.hold);
        TextView label = findViewById(R.id.score);
        
        hold.setEnabled(false);
        label.setText("Your score: " + player_score + " Computer score: " + computer_score + " Your turn score: " + session_score);
    }

    public void checkScore(){
        handler.removeCallbacks(runnable);
        TextView label = findViewById(R.id.score);
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);

        // Check if game should end (first to reach 100 points wins)
        if (player_score >= 100 || computer_score >= 100) {
            if (player_score > computer_score) {
                label.setText("You Won! Final Score - You: " + player_score + " Computer: " + computer_score);
            } else if (player_score == computer_score) {
                label.setText("Draw! Final Score - You: " + player_score + " Computer: " + computer_score);
            } else {
                label.setText("Computer Won! Final Score - You: " + player_score + " Computer: " + computer_score);
            }
            roll.setEnabled(false);
            hold.setEnabled(false);
        } else {
            // Game continues, enable player's turn
            label.setText("Your turn! Your score: " + player_score + " Computer score: " + computer_score + " Your turn score: 0");
            roll.setEnabled(true);
            hold.setEnabled(false);
            session_score = 0;
            turn_score = 0;
        }
    }




    public void computerTurn(){
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);
        roll.setEnabled(false);
        hold.setEnabled(false);

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1000);

    }


    public void reset(View view){
        handler.removeCallbacks(runnable);
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);
        TextView label = findViewById(R.id.score);
        ImageView diceImage = findViewById(R.id.imageView);
        
        // Reset all game state
        player_score = 0;
        session_score = 0;
        computer_score = 0;
        turn_score = 0;
        temp = 0;
        f = 0;
        
        // Reset UI
        roll.setEnabled(true);
        hold.setEnabled(false);
        diceImage.setImageResource(R.drawable.dice1);
        label.setText("Your score: " + player_score + " Computer score: " + computer_score + " Your turn score: " + session_score);
    }

    public void hold(View view){
        handler.removeCallbacks(runnable);
        TextView label = findViewById(R.id.score);
        
        // Add session score to total player score
        player_score = temp + session_score;
        temp = player_score; // Update the saved score
        session_score = 0; // Reset session score for next turn
        
        label.setText("Your score: " + player_score + " computer score: " + computer_score + " your turn score: " + session_score);
        computerTurn();
    }

    public void rollDice(View view){
        ImageView diceImage = findViewById(R.id.imageView);
        TextView label = findViewById(R.id.score);
        Button hold = findViewById(R.id.hold);

        int r = random.nextInt(6) + 1; // Generate 1-6 instead of 0-5
        
        if (r == 1) {
            // Rolling a 1 loses the turn and session score
            diceImage.setImageResource(R.drawable.dice1);
            session_score = 0;
            label.setText("Your score: " + temp + " computer score: " + computer_score + " your turn score: " + session_score);
            hold.setEnabled(false);
            computerTurn();
        } else {
            // Rolling 2-6 adds to session score
            switch (r) {
                case 2:
                    diceImage.setImageResource(R.drawable.dice2);
                    break;
                case 3:
                    diceImage.setImageResource(R.drawable.dice3);
                    break;
                case 4:
                    diceImage.setImageResource(R.drawable.dice4);
                    break;
                case 5:
                    diceImage.setImageResource(R.drawable.dice5);
                    break;
                case 6:
                    diceImage.setImageResource(R.drawable.dice6);
                    break;
            }
            
            session_score += r;
            player_score = temp + session_score; // Total score = previous score + session score
            label.setText("Your score: " + temp + " computer score: " + computer_score + " your turn score: " + session_score);
            hold.setEnabled(true);
        }
    }
}
