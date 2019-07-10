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

    public int player_score = 0;
    public int session_score = 0;
    public int computer_score = 0;
    public int turn_score = 0;
    Random random = new Random();
    int temp = 0;
    int temp2 = 0;
    int f = 0;


    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ImageView diceImage = findViewById(R.id.imageView);
            TextView label = findViewById(R.id.score);

            int r = random.nextInt(6);
            if (r != 0) {
                if (r == 1 || r == 5) {
                    if (r == 1) {
                        diceImage.setImageResource(R.drawable.dice2);
                        computer_score += 2;
                        turn_score += 2;
                        label.setText("player's score: " + player_score + " computer score: " + temp2 + " computer's turn score: " + turn_score);
                        f = 0;

                    } else {
                        diceImage.setImageResource(R.drawable.dice6);
                        computer_score += 6;
                        turn_score += 6;
                        label.setText("player's score: " + player_score + " computer score: " + temp2 + " computer's turn score: " + turn_score);
                        f = 0;
                    }
                    if (computer_score > player_score){
                    int choice = random.nextInt(2);
                    if (choice == 1) {
                        temp2 = computer_score;
                        checkScore();
                        handler.removeCallbacks(this);
                        f = 1;

                    }
                    }
                } else {

                    if (r == 2) {
                        diceImage.setImageResource(R.drawable.dice3);
                        computer_score += 3;
                        turn_score += 3;
                        label.setText("player's score: " + player_score + " computer score: " + temp2 + " computer's turn score: " + turn_score);
                        f = 0;


                    } else if (r == 3) {
                        diceImage.setImageResource(R.drawable.dice4);
                        computer_score += 4;
                        turn_score += 4;
                        label.setText("player's score: " + player_score + " computer score: " + temp2 + " computer's turn score: " + turn_score);
                        f = 0;

                    } else if (r == 4) {
                        diceImage.setImageResource(R.drawable.dice5);
                        computer_score += 5;
                        turn_score += 5;
                        label.setText("player's score: " + player_score + " computer score: " + temp2 + " computer's turn score: " + turn_score);
                        f = 0;

                    }

                }
            } else {
                diceImage.setImageResource(R.drawable.dice1);
                temp2 = computer_score;
                turn_score = 0;
                label.setText("player's score: " + player_score + " computer score: " + temp2 + " computer's turn score: " + turn_score);
                checkScore();
                handler.removeCallbacks(this);
                f = 1;

            }
            if (f == 0) {
                handler.postDelayed(this, 1000);
            }
            else {
                f = 0;
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

    public void checkScore(){
        handler.removeCallbacks(runnable);
        TextView label = findViewById(R.id.score);
        Button roll = findViewById(R.id.roll);
        Button hold = findViewById(R.id.hold);

        if (player_score > computer_score){
            label.setText("You Won !");
        }
        else if (player_score == computer_score) {
            label.setText("Draw !");
        }
        else {
            label.setText("Computer Won !");
            }
        player_score = 0;
        session_score = 0;
        computer_score = 0;
        turn_score = 0;
        temp = 0;
        temp2 = 0;
        roll.setEnabled(false);
        hold.setEnabled(false);
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
        roll.setEnabled(true);
        TextView label = findViewById(R.id.score);
        player_score = 0;
        session_score = 0;
        computer_score = 0;
        turn_score = 0;
        temp = 0;
        temp2 = 0;
        label.setText("Your score: "+player_score+" computer score: "+computer_score+" your turn score: "+session_score);
    }

    public void hold(View view){
        handler.removeCallbacks(runnable);
        TextView label = findViewById(R.id.score);
        session_score = 0;
        temp = player_score;
        label.setText("Your score: "+player_score+" computer score: "+computer_score+" your turn score: "+session_score);
        computerTurn();
    }

    public void rollDice(View view){
        ImageView diceImage = findViewById(R.id.imageView);
        TextView label = findViewById(R.id.score);
        Button hold = findViewById(R.id.hold);
        hold.setEnabled(false);

        int r = random.nextInt(6);
        if ( r == 0 ){
            diceImage.setImageResource(R.drawable.dice1);
            session_score = 0;
            player_score = 0;
            label.setText("Your score: "+temp+" computer score: "+computer_score+" your turn score: "+session_score);
            computerTurn();

        }
        else if ( r == 1 ){
            diceImage.setImageResource(R.drawable.dice2);
            hold.setEnabled(true);
            player_score += 2;
            session_score += 2;
            label.setText("Your score: "+temp+" computer score: "+computer_score+" your turn score: "+session_score);

        }
        else if ( r == 2 ){
            diceImage.setImageResource(R.drawable.dice3);
            player_score += 3;
            session_score += 3;
            label.setText("Your score: "+temp+" computer score: "+computer_score+" your turn score: "+session_score);

        }
        else if ( r == 3 ){
            diceImage.setImageResource(R.drawable.dice4);
            player_score += 4;
            session_score += 4;
            label.setText("Your score: "+temp+" computer score: "+computer_score+" your turn score: "+session_score);

        }
        else if ( r == 4 ){
            diceImage.setImageResource(R.drawable.dice5);
            player_score += 5;
            session_score += 5;
            label.setText("Your score: "+temp+" computer score: "+computer_score+" your turn score: "+session_score);

        }
        else{
            diceImage.setImageResource(R.drawable.dice6);
            hold.setEnabled(true);
            player_score += 6;
            session_score += 6;
            label.setText("Your score: "+temp+" computer score: "+computer_score+" your turn score: "+session_score);

        }

    }
}
