package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;
    private int roundCount;
    private int player1Point;
    private int player2Point;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_player1);
        textViewPlayer2 = findViewById(R.id.text_view_player2);

        //looping 2 dimensional array
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "button" + i + j;
                int resourceID = getResources().getIdentifier(buttonID,"id", getPackageName());
                buttons[i][j] = findViewById(resourceID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);

        buttonReset.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")){
            return;
        }

        if (player1Turn){
            ((Button) v).setText("X");
        }else{
            ((Button) v).setText("O");
        }

        roundCount++;

        if(checkWin()){
            if(player1Turn){
                player1Win();
            }else{
                player2Win();
            }
        }else if(roundCount == 9){
            draw();
        }else{
            player1Turn = !player1Turn;
        }
    }

    //check game winner by checking 3 combination with same symbol
    private boolean checkWin(){
        String[][] field = new String[3][3];
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //checking row combination
        for (int i = 0; i < 3; i++){
            if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!field[i][0].equals("")){
                return true;
            }
        }

        //checking column combination
        for (int i = 0; i < 3; i++){
            if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!field[0][i].equals("")){
                return true;
            }
        }

        //checking diagonal combination - 1
        if(field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&&!field[0][0].equals("")){
            return true;
        }

        //checking diagonal combination - 2
        if(field[0][2].equals(field[1][1])&&field[0][2].equals(field[2][0])&&!field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    //add point to player 1 if player 1 wins
    private void player1Win(){
        player1Point++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePoint();
        resetBoard();
    }

    //add point to player 2 if player 2 wins
    private void player2Win(){
        player2Point++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePoint();
        resetBoard();
    }

    //if the game already moved 9 times, game = draw
    private void draw(){
        Toast.makeText(this,"Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //update TextView point of player 1 & 2
    private void updatePoint(){
        textViewPlayer1.setText("Player 1: " + player1Point);
        textViewPlayer2.setText("Player 2: " + player2Point);
    }

    //reset only the board in the game
    private void resetBoard(){
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    //reset board and the point of the game
    private void resetGame(){
        player1Point = 0;
        player2Point = 0;
        updatePoint();
        resetBoard();
    }

    //saving the game, so when the layout tilted it will still there
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Point", player1Point);
        outState.putInt("player2Point", player2Point);
        outState.putBoolean("player1Turn",player1Turn);
    }

    //saving the game, so when the layout tilted it will still there
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Point = savedInstanceState.getInt("player1Point");
        player2Point = savedInstanceState.getInt("player2Point");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}