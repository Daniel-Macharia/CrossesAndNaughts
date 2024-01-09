package com.example.crossesandnaughts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class ScoresFragment extends Fragment {

    private TextView ten, twenty, fifty, percentTen, percentTwenty, percentFifty;
    private LinearProgressIndicator seekTen, seekTwenty, seekFifty;

    private int index;

    public ScoresFragment()
    {
        //empty constructor
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        View v = inflater.inflate( R.layout.scores_fragment, container, false);

        try {

            ten = v.findViewById( R.id.tenGames );
            twenty = v.findViewById( R.id.twentyGames );
            fifty = v.findViewById( R.id.fiftyGames );

            percentTen = v.findViewById( R.id.percentTen );
            percentTwenty = v.findViewById( R.id.percentTwenty );
            percentFifty = v.findViewById( R.id.percentFifty );

            seekTen = v.findViewById( R.id.seekTen );
            seekTwenty = v.findViewById( R.id.seekTwenty );
            seekFifty = v.findViewById( R.id.seekFifty );

            initFragmentData( index );


        }catch( Exception e )
        {
            Toast.makeText( getContext(), "Error from app: " + e, Toast.LENGTH_SHORT).show();
        }


        return v;
    }

    public void setTabId( int id )
    {
        this.index = id;
    }

    private void initFragmentData( int index)
    {
        try
        {
            String tenString = "", twentyString = "", fiftyString = "";

            double total_ten = 10, total_twenty = 20, total_fifty = 50;
            double wins_ten = 0, wins_twenty = 0, wins_fifty = 0;

            double percent_ten = 0, percent_twenty = 0, percent_fifty = 0;

            switch( index )
            {
                case 0:
                    wins_ten = MainActivity.p.getInt(MainActivity.winsTenEasy, 0);
                    wins_twenty = MainActivity.p.getInt( MainActivity.winsTwentyEasy, 0);
                    wins_fifty = MainActivity.p.getInt(MainActivity.winsFiftyEasy, 0);
                    break;
                case 1:
                    wins_ten = MainActivity.p.getInt(MainActivity.winsTenNormal, 0);
                    wins_twenty = MainActivity.p.getInt( MainActivity.winsTwentyNormal, 0);
                    wins_fifty = MainActivity.p.getInt(MainActivity.winsFiftyNormal, 0);
                    break;
                case 2:
                    wins_ten = MainActivity.p.getInt(MainActivity.winsTenHard, 0);
                    wins_twenty = MainActivity.p.getInt( MainActivity.winsTwentyHard, 0);
                    wins_fifty = MainActivity.p.getInt(MainActivity.winsFiftyHard, 0);
                    break;
            }

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
        }catch( Exception e )
        {
            Toast.makeText( getContext(), "Error from app: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
