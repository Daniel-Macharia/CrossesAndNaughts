package com.example.crossesandnaughts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static SimpleReflexAgent agent;
    private static ImageView one, two, three, four, five, six, seven, eight, nine;

    private ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

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

            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createMenus( v );
                }
            });

            InternalEnvironmentState.context = MainActivity.this;
            InternalEnvironmentState.init();
            InternalEnvironmentState.setUserMark("cross");

            agent = new SimpleReflexAgent( MainActivity.this );

            setClickListeners( MainActivity.this );



        }catch( Exception e )
        {
            Toast.makeText(this, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    private void createMenus( View view)
    {
        Toast.makeText(this, "Clicked menus", Toast.LENGTH_SHORT).show();

        PopupMenu menus = new PopupMenu( view.getContext(), view);
        menus.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int id = item.getItemId();

                if( id == R.id.item_level )
                {
                    Toast.makeText(MainActivity.this, "Clicked level", Toast.LENGTH_SHORT).show();
                    PopupMenu sub = new PopupMenu( view.getContext(), view );

                   sub.getMenu().add("Easy").setCheckable(true).setChecked(true);
                   sub.getMenu().add("Normal").setCheckable(true);
                   sub.getMenu().add("Hard").setCheckable(true);

                   sub.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {

                           String title = item.getTitle().toString();

                           if( title.equals("Easy") )
                           {
                               Toast.makeText(MainActivity.this, "Clicked Easy", Toast.LENGTH_SHORT).show();
                               //agent = null;
                               agent = new SimpleReflexAgent( MainActivity.this );
                               return true;
                           }
                           else if( title.equals("Normal"))
                           {
                               Toast.makeText(MainActivity.this, "Clicked Normal", Toast.LENGTH_SHORT).show();
                               //agent = null;
                               agent = new GoalBasedAgent( MainActivity.this );
                               return true;
                           }
                           else if( title.equals("Hard"))
                           {
                               Toast.makeText(MainActivity.this, "Clicked Hard", Toast.LENGTH_SHORT).show();
                               //agent = null;
                               agent = new UtilityAgent( MainActivity.this );
                               return true;
                           }

                           return true;
                       }
                   });

                   sub.show();

                    return true;
                }
                else if( id == R.id.item_help )
                {
                    Toast.makeText(MainActivity.this, "clicked help", Toast.LENGTH_SHORT).show();
                    Intent helpIntent = new Intent( MainActivity.this, Help.class );
                    startActivity( helpIntent );
                    return true;
                }
                else if( id == R.id.item_email )
                {
                    Toast.makeText(MainActivity.this, "clicked email", Toast.LENGTH_SHORT).show();
                    Intent emailIntent = new Intent( MainActivity.this, EmailDevs.class );
                    startActivity( emailIntent );
                    return true;
                }

                return false;

            }
        });

        menus.inflate( R.menu.main_menus );
        menus.show();
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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
                            String s = "";
                            if( InternalEnvironmentState.userWon )
                                s += "You Win!";
                            else if( InternalEnvironmentState.agentWon )
                                s += "You Loose!";
                            else
                                s += "We Draw!";

                            Dialog d = new Dialog( context );
                            TextView tv = new TextView( context );
                            tv.setBackgroundColor( android.view.ViewGroup.LayoutParams.MATCH_PARENT );
                            tv.setTextColor( context.getResources().getColor(R.color.black) );
                            tv.setText( "Game Over!\n\n" + s );
                            d.setContentView( tv );
                            d.show();

                            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog){
                                    InternalEnvironmentState.refresh();
                                    agent.play();
                                }
                            });
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

