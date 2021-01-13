package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayPage extends AppCompatActivity {
    int playerTurn = 0;
    int board[] = createBoard();
    int turns = 0;
    boolean winnerFound = false;
    String playerOneName;
    String playerTwoName;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Bundle bundle = getIntent().getExtras();
        playerOneName = bundle.getString("playerOne");
        playerTwoName = bundle.getString("playerTwo");

        TextView textPlayer1 = (TextView) findViewById(R.id.textPlayer1);
        TextView textPlayer2 = (TextView) findViewById(R.id.textPlayer2);

        textPlayer1.setText(playerOneName + " - ");
        textPlayer2.setText(playerTwoName + " - ");

        MediaPlayer start = MediaPlayer.create(this , R.raw.again);
        start.start();

        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    }
    public int[] createBoard() {
        int board[] = new int[9];
        for(int i=0; i<9; i++) board[i] = -1;
        return board;
    }

    public void playMove(View view) {
        ImageView counter = (ImageView) view;
        TextView textPlayer1 = (TextView) findViewById(R.id.textPlayer1);
        ImageView imagePlayer1 = (ImageView) findViewById(R.id.imagePlayer1);
        TextView textPlayer2 = (TextView) findViewById(R.id.textPlayer2);
        ImageView imagePlayer2 = (ImageView) findViewById(R.id.imagePlayer2);

        int positionClicked = Integer.parseInt(counter.getTag().toString()) - 1;

        if(winnerFound == true) {
            Toast.makeText(this, "Click on Play Again!", Toast.LENGTH_SHORT).show();
            MediaPlayer error = MediaPlayer.create(this , R.raw.error);
            error.start();
            return;
        }

        if(board[positionClicked] != -1){
            Toast.makeText(this, "Please select an empty cell!", Toast.LENGTH_SHORT).show();
            MediaPlayer error = MediaPlayer.create(this , R.raw.error);
            error.start();
            return;
        }

        if(playerTurn == 0) {
            counter.setImageResource(R.drawable.x);
            counter.setAlpha(1f);
            board[positionClicked] = playerTurn;
            textPlayer1.setAlpha(0);
            textPlayer2.setAlpha(1);
            imagePlayer1.setAlpha(0f);
            imagePlayer2.setAlpha(1f);
        }
        else{
            counter.setImageResource(R.drawable.circle);
            counter.setAlpha(1f);
            board[positionClicked] = playerTurn;
            textPlayer1.setAlpha(1);
            textPlayer2.setAlpha(0);
            imagePlayer1.setAlpha(1f);
            imagePlayer2.setAlpha(0f);
        }
        turns += 1;
        if(checkWinner()){
            winnerFound = true;
            TextView winnerText = (TextView) findViewById(R.id.winnerText);
            winnerText.setText("Player " + Integer.toString(playerTurn+1) + " Wins!");
            MediaPlayer win = MediaPlayer.create(this , R.raw.win);
            win.start();
            textPlayer1.setAlpha(0);
            textPlayer2.setAlpha(0);
            imagePlayer1.setAlpha(0f);
            imagePlayer2.setAlpha(0f);
            winnerText.setAlpha(1f);
            return;
        }

        if(turns == 9) {
            winnerFound = true;
            TextView winnerText = (TextView) findViewById(R.id.winnerText);
            winnerText.setText("It's a draw!");
            MediaPlayer win = MediaPlayer.create(this , R.raw.win);
            win.start();
            textPlayer1.setAlpha(0);
            textPlayer2.setAlpha(0);
            imagePlayer1.setAlpha(0f);
            imagePlayer2.setAlpha(0f);
            winnerText.setAlpha(1f);
            return;
        }

        MediaPlayer click = MediaPlayer.create(this, R.raw.click);
        click.start();

        playerTurn = 1 - playerTurn ;
    }

    public boolean checkWinner() {
//        rows
        for(int i=0; i<9; i+=3) {
            if (board[i] == playerTurn && board[i] == board[i + 1] && board[i] == board[i + 2])
                return true;
        }

//        columns
        for(int j=0; j<9; j+=3){
            int i=j/3;
            if (board[i] == playerTurn && board[i] == board[i + 3] && board[i] == board[i + 6])
                return true;
        }

//        diagonals
        return (board[0]==playerTurn && board[0]==board[4] && board[0]==board[8]) || (board[2]==playerTurn && board[2]==board[4] && board[2]==board[6]);
    }

    public void playAgain(View view) {
        winnerFound = false;
        playerTurn = 0;
        turns = 0;

        TextView winnerText = (TextView) findViewById(R.id.winnerText);
        winnerText.setAlpha(0f);

        TextView textPlayer1 = (TextView) findViewById(R.id.textPlayer1);
        ImageView imagePlayer1 = (ImageView) findViewById(R.id.imagePlayer1);
        TextView textPlayer2 = (TextView) findViewById(R.id.textPlayer2);
        ImageView imagePlayer2 = (ImageView) findViewById(R.id.imagePlayer2);
        textPlayer1.setAlpha(1f);
        imagePlayer1.setAlpha(1f);
        textPlayer2.setAlpha(0f);
        imagePlayer2.setAlpha(0f);

        for(int i=0; i<9; i++) board[i] = -1;

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            ImageView child = (ImageView) gridLayout.getChildAt(i);
            child.setImageDrawable(null);
        }
        MediaPlayer again = MediaPlayer.create(this , R.raw.again);
        again.start();

        vibe.vibrate(80);
    }
}