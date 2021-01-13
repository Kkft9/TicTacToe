package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsPage extends AppCompatActivity {
    String playerOneName;
    String playerTwoName;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void goToPlayPage(View view) {
        TextView playerOne = findViewById(R.id.playerOne);
        TextView playerTwo = findViewById(R.id.playerTwo);

        playerOneName = playerOne.getText().toString();
        playerTwoName = playerTwo.getText().toString();

        vibe.vibrate(80);

        if(playerOneName.length() == 0 || playerTwoName.length() == 0) {
            Toast.makeText(this, "Please enter the names of the players!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this , PlayPage.class);
        Bundle bundle = new Bundle();
        bundle.putString("playerOne" , playerOneName);
        bundle.putString("playerTwo" , playerTwoName);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}