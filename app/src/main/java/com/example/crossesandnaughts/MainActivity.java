package com.example.crossesandnaughts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static TextView current_scores;
    private static SimpleReflexAgent agent;
    private static ImageView one, two, three, four, five, six, seven, eight, nine;

    private String level = "level";
    public static int totalGamesPlayed, totalGamesWon;
    public static String winsTenEasy = "winsTenEasy", winsTwentyEasy = "winsTwentyEasy", winsFiftyEasy = "winsFiftyEasy";

    public static String winsTenNormal = "winsTenNormal", winsTwentyNormal = "winsTwentyNormal", winsFiftyNormal = "winsFiftyNormal";

    public static String winsTenHard = "winsTenHard", winsTwentyHard = "winsTwentyHard", winsFiftyHard = "winsFiftyHard";

    private ImageView menu;

    public static SharedPreferences p;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            current_scores = findViewById( R.id.current_scores );

            menu = findViewById( R.id.menu );

            one = findViewById( R.id.one );
            two = findViewById( R.id.two );
            three = findViewById( R.id.three );

            four = findViewById( R.id.four );
            five = findViewById( R.id.five );
            six = findViewById( R.id.six );

            seven = findViewById( R.id.seven );
            eight = findViewById( R.id.eight );
            nine = findViewById( R.id.nine );

            current_scores.setText("wins\n" + totalGamesWon + " / " + totalGamesPlayed);
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createMenus( v );
                }
            });

            InternalEnvironmentState.context = MainActivity.this;
            InternalEnvironmentState.init();
            InternalEnvironmentState.setUserMark("cross");

            totalGamesPlayed = 0;
            totalGamesWon = 0;

            p = this.getPreferences( Context.MODE_PRIVATE );
            editor = p.edit();

            int levelId = p.getInt(level, 0);

            editor.apply();

            switch( levelId )
            {
                case 1:
                    agent = new GoalBasedAgent( MainActivity.this );
                    break;
                case 2:
                    agent = new UtilityAgent( MainActivity.this );
                    break;
                default:
                    agent = new SimpleReflexAgent( MainActivity.this );
            }

            setClickListeners( MainActivity.this );



        }catch( Exception e )
        {
            Toast.makeText(this, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean exceededHighScore( Context context, int totalGames, int totalWins)
    {
        boolean exceeded = false;
        try {
            String val = "New High Score!\n\n";

            double total_ten = 10, total_twenty = 20, total_fifty = 50;
            double wins_ten = 0, wins_twenty = 0, wins_fifty = 0;
            double percentage = 0;

            int level = p.getInt("level", 0);
            switch ( level )
            {
                case 0:
                    wins_ten = p.getInt(winsTenEasy, 0);
                    wins_twenty = p.getInt( winsTwentyEasy, 0);
                    wins_fifty = p.getInt(winsFiftyEasy, 0);
                    break;
                case 1:
                    wins_ten = p.getInt(winsTenNormal, 0);
                    wins_twenty = p.getInt( winsTwentyNormal, 0);
                    wins_fifty = p.getInt(winsFiftyNormal, 0);
                    break;
                case 2:
                    wins_ten = p.getInt(winsTenHard, 0);
                    wins_twenty = p.getInt( winsTwentyHard, 0);
                    wins_fifty = p.getInt(winsFiftyHard, 0);
                    break;
            }

            if( totalGames > 0 )
            {
                double t = totalGames, w = totalWins;
                percentage = w / t * 100;
            }


            if( totalGames == total_ten )
            {
                if( totalWins > wins_ten )
                {
                    exceeded = true;
                    val += "you've won " + totalWins + " games out of " + totalGames + "\n\nThat's " + (int)percentage + "% of the games";

                    if( level == 0 )
                    {
                        editor.putInt( winsTenEasy, totalWins);
                    }
                    else if( level == 1 )
                    {
                        editor.putInt( winsTenNormal, totalWins);
                    }
                    else if( level == 2 )
                    {
                        editor.putInt( winsTenHard, totalWins);
                    }

                    editor.apply();

                }
            }
            else if( totalGames == total_twenty )
            {
               if( totalWins > wins_twenty )
               {
                   exceeded = true;
                   val += "you've won " + totalWins + " games out of " + totalGames + "\n\nThat's " + (int)percentage + "% of the games";

                   if( level == 0 )
                   {
                       editor.putInt( winsTwentyEasy, totalWins);
                   }
                   else if( level == 1 )
                   {
                       editor.putInt( winsTwentyNormal, totalWins);
                   }
                   else if( level == 2 )
                   {
                       editor.putInt( winsTwentyHard, totalWins);
                   }

                   editor.apply();

               }
            }
            else if( totalGames == total_fifty )
            {
                if( totalWins > wins_fifty )
                {
                    exceeded = true;
                    val += "you've won " + totalWins + " games out of " + totalGames + "\n\nThat's " + (int)percentage + "% of the games";

                    if( level == 0 )
                    {
                        editor.putInt( winsFiftyEasy, totalWins);
                    }
                    else if( level == 1 )
                    {
                        editor.putInt( winsFiftyNormal, totalWins);
                    }
                    else if( level == 2 )
                    {
                        editor.putInt( winsFiftyHard, totalWins);
                    }

                    editor.apply();
                    
                }

                totalGamesPlayed = 0;
                totalGamesWon = 0;

            }

            if( percentage <= 35 )
            {
                val += "\n\nWell Done!";
            }
            else if( percentage <= 70 )
            {
                val += "\n\nYou're a smart guy!";
            }
            else if( percentage > 70 )
            {
                val += "\n\nGenius!";
            }

            if( exceeded )
            {
                Dialog d = new Dialog( context );
                TextView tv = new TextView( context );
                tv.setBackgroundColor( context.getResources().getColor( R.color.white ) );
                tv.setTextColor( context.getResources().getColor( R.color.blue ) );
                tv.setPadding( 8, 8, 8, 8);

                tv.setText(val);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.setContentView( tv );
                d.show();
            }

        }catch( Exception e ) {
            Toast.makeText(context, "Error from application: " + e, Toast.LENGTH_SHORT).show();
        }
        return exceeded;

    }

    private void updateLevel( int levelId )
    {
        try {
            {
                editor.putInt("level", levelId);
                editor.apply();

                totalGamesPlayed = 0;
                totalGamesWon = 0;

                InternalEnvironmentState.refresh();

                current_scores.setText("wins\n" + totalGamesWon + " / " + totalGamesPlayed);
            }
        }catch( Exception e )
        {
            Toast.makeText(getApplicationContext(), "Error from application: " + e , Toast.LENGTH_SHORT).show();
        }
    }

    private void createMenus( View view)
    {
        try {
            PopupMenu menus = new PopupMenu( view.getContext(), view);

            menus.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    int id = item.getItemId();

                    if( id == R.id.level_easy)
                    {
                        item.setChecked(true);
                        agent = new SimpleReflexAgent( MainActivity.this );

                        updateLevel( 0 );

                        return true;
                    }
                    else
                    if( id == R.id.level_normal)
                    {
                        item.setChecked(true);
                        agent = new GoalBasedAgent( MainActivity.this );

                        updateLevel( 1 );

                        return true;
                    }
                    else
                    if( id == R.id.level_hard)
                    {
                        item.setChecked(true);
                        agent = new UtilityAgent( MainActivity.this );

                        updateLevel( 2 );

                        return true;
                    }
                    else
                    if( id == R.id.item_level )
                    {
                        try
                        {
                            SubMenu sub = item.getSubMenu();

                            int level = p.getInt("level", 0);

                            sub.getItem(level).setChecked(true);
                        }catch( Exception e )
                        {
                            Toast.makeText(MainActivity.this, "error: " + e, Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                    else if( id == R.id.item_multiplayer )
                    {
                        Toast.makeText(getApplicationContext(), "I'm sorry, this feature is not yet available.", Toast.LENGTH_SHORT).show();
                        //initMultiplayer();
                        return true;
                    }
                    else if( id == R.id.item_high_score )
                    {
                        Intent intent = new Intent( MainActivity.this, Scores.class );
                        startActivity( intent );
                        return true;
                    }
                    else if( id == R.id.item_help )
                    {
                        Intent helpIntent = new Intent( MainActivity.this, Help.class );
                        startActivity( helpIntent );
                        return true;
                    }
                    else if( id == R.id.item_email )
                    {
                        Intent emailIntent = new Intent( MainActivity.this, EmailDevs.class );
                        startActivity( emailIntent );
                        return true;
                    }


                    return false;

                }
            });

            menus.inflate( R.menu.main_menus );
            menus.show();
        }catch ( Exception e )
        {
            Toast.makeText(this, "Error from app: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void initMultiplayer()
    {
        Intent intent = new Intent(  MainActivity.this, MultiplayerActivity.class);
        startActivity( intent );
    }

    public static void resetGame( Context context )
    {
        try
        {
            one.setImageResource( R.drawable.blank );
            two.setImageResource( R.drawable.blank );
            three.setImageResource( R.drawable.blank );

            four.setImageResource( R.drawable.blank );
            five.setImageResource( R.drawable.blank );
            six.setImageResource( R.drawable.blank );

            seven.setImageResource( R.drawable.blank );
            eight.setImageResource( R.drawable.blank );
            nine.setImageResource( R.drawable.blank );


        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e , Toast.LENGTH_SHORT).show();
        }
    }

    public static void displayVictoryMessage( Context context )
    {
        try {
            String s = "Game Over!\n\n";

            if( InternalEnvironmentState.userWon )
            {
                s += "You Win!";
                totalGamesWon++;
            }
            else if( InternalEnvironmentState.agentWon )
                s += "You Loose!";
            else
                s += "We Draw!";

            totalGamesPlayed++;

            if( exceededHighScore( context, totalGamesPlayed, totalGamesWon ) ) {
                InternalEnvironmentState.refresh();
                agent.play();
                return;
            }

            Dialog d = new Dialog( context );
            TextView tv = new TextView( context );
            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
            tv.setTextColor( context.getResources().getColor(R.color.black) );

            tv.setText( s );

            d.setContentView( tv );
            d.show();

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });

            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog){
                    InternalEnvironmentState.refresh();
                    agent.play();
                }
            });

            current_scores.setText("wins\n" + totalGamesWon + " / " + totalGamesPlayed);

        }catch( Exception e )
        {
            Toast.makeText(context, "Error from application: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public static void setClickListeners( Context context)
    {
        try
        {
            one.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 1);
                        InternalEnvironmentState.update( context, "user", 1);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }
                    catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            two.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 2);
                        InternalEnvironmentState.update( context, "user", 2);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            three.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 3);
                        InternalEnvironmentState.update( context, "user", 3);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            four.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 4);
                        InternalEnvironmentState.update( context, "user", 4);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            five.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 5);
                        InternalEnvironmentState.update( context, "user", 5);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            six.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 6);
                        InternalEnvironmentState.update( context, "user", 6);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            seven.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 7);
                        InternalEnvironmentState.update( context, "user", 7);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            eight.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 8);
                        InternalEnvironmentState.update( context, "user", 8);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            nine.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    try
                    {
                        unsetClickListener( context, "user", 9);
                        InternalEnvironmentState.update( context, "user", 9);

                        if( InternalEnvironmentState.gameOver( context ) )
                        {
                            displayVictoryMessage( context );
                        }
                        else
                        {
                            agent.play();
                        }
                    }catch( Exception e )
                    {
                        Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public static void unsetClickListener( Context context, String player, int id)
    {

        try
        {
            switch (id) {
                case 1:
                    one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    one.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 2:
                    two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    two.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 3:
                    three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    three.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 4:
                    four.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    four.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 5:
                    five.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    five.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 6:
                    six.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    six.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 7:
                    seven.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    seven.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 8:
                    eight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    eight.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;


                case 9:
                    nine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    nine.setImageResource( InternalEnvironmentState.getPlayerMark( player ).equals("cross") ? R.drawable.cross : R.drawable.naught );

                    break;

            }
        }catch( WindowManager.BadTokenException e)
        {
            Toast.makeText(context, "Caught bad token exception while unsetting click listener", Toast.LENGTH_SHORT).show();
        }
        catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

    }
}

