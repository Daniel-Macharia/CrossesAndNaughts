package com.example.crossesandnaughts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class Scores  extends AppCompatActivity {

    private TextView ten, twenty, fifty, percentTen, percentTwenty, percentFifty;
    private LinearProgressIndicator seekTen, seekTwenty, seekFifty;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.scores );

        ten = findViewById( R.id.tenGames );
        twenty = findViewById( R.id.twentyGames );
        fifty = findViewById( R.id.fiftyGames );

        percentTen = findViewById( R.id.percentTen );
        percentTwenty = findViewById( R.id.percentTwenty );
        percentFifty = findViewById( R.id.percentFifty );

        seekTen = findViewById( R.id.seekTen );
        seekTwenty = findViewById( R.id.seekTwenty );
        seekFifty = findViewById( R.id.seekFifty );

        String tenString = "", twentyString = "", fiftyString = "";

        double total_ten = 10, total_twenty = 20, total_fifty = 50;
        double wins_ten, wins_twenty, wins_fifty;

        double percent_ten = 0, percent_twenty = 0, percent_fifty = 0;

        wins_ten = MainActivity.p.getInt(MainActivity.winsTen, 0);
        wins_twenty = MainActivity.p.getInt( MainActivity.winsTwenty, 0);
        wins_fifty = MainActivity.p.getInt(MainActivity.winsFifty, 0);

        percent_ten = wins_ten / total_ten * 100;
        percent_twenty = wins_twenty / total_twenty * 100;
        percent_fifty = wins_fifty / total_fifty * 100;

        seekTen.setProgress( (int)percent_ten );
        seekTwenty.setProgress( (int)percent_twenty );
        seekFifty.setProgress( (int)percent_fifty );

        percentTen.setText( (int)percent_ten + "%");
        percentTwenty.setText( (int)percent_twenty + "%");
        percentFifty.setText( (int)percent_fifty + "%");

        tenString += "\t\tTen in a row\n\n" + (int)wins_ten + " games won out of " + (int)total_ten;
                //"\nYou have won  " + (int)percent_ten + "% of the games.";

        twentyString += "\t\tTwenty in a row\n\n" + (int)wins_twenty + " games won out of " + (int)total_twenty;
               // "\nYou have won  " + (int)percent_twenty + "% of the games.";

        fiftyString += "\t\tFifty in a row\n\n" + (int)wins_fifty + " games won out of " + (int)total_fifty;
                //"\nYou have won  " + (int)percent_fifty + "% of the games.";

        ten.setText( tenString );
        twenty.setText( twentyString );
        fifty.setText( fiftyString );
    }
}
