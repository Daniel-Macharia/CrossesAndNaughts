package com.example.crossesandnaughts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static ImageView one, two, three, four, five, six, seven, eight, nine;
    //private Map<Integer, String> marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            one = findViewById( R.id.one );
            two = findViewById( R.id.two );
            three = findViewById( R.id.three );

            four = findViewById( R.id.four );
            five = findViewById( R.id.five );
            six = findViewById( R.id.six );

            seven = findViewById( R.id.seven );
            eight = findViewById( R.id.eight );
            nine = findViewById( R.id.nine );

            //initMap();

            InternalEnvironmentState.init( MainActivity.this );
            InternalEnvironmentState.setUserMark("cross");
            SimpleReflexAgent agent = new SimpleReflexAgent( MainActivity.this );

            one.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked one", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 1 );
                    agent.play();
                }
            });

            two.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked two", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 2 );
                    agent.play();
                }
            });

            three.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked three", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 3 );
                    agent.play();
                }
            });

            four.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked four", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 4 );
                    agent.play();
                }
            });

            five.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked five", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 5 );
                    agent.play();
                }
            });

            six.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked six", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 6 );
                    agent.play();
                }
            });

            seven.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked seven", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 7 );
                    agent.play();
                }
            });

            eight.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked eight", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 8 );
                    agent.play();
                }
            });

            nine.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    Toast.makeText( MainActivity.this, "Clicked nine", Toast.LENGTH_SHORT).show();
                    unsetClickListener( MainActivity.this, "user", 9 );
                    agent.play();
                }
            });

        }catch( Exception e )
        {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }

    }

    /*
    private void initMap()
    {
        marks.put( 1, "one");
        marks.put( 2, "two");
        marks.put( 3, "three");

        marks.put( 4, "four");
        marks.put( 5, "five");
        marks.put( 6, "six");

        marks.put( 7, "seven");
        marks.put( 8, "eight");
        marks.put( 9, "nine");
    } */

    public static void unsetClickListener(Context context, String player, int id)
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

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        one.setImageResource( R.drawable.cross );
                    else
                        one.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 2:
                    two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        two.setImageResource( R.drawable.cross );
                    else
                        two.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 3:
                    three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        three.setImageResource( R.drawable.cross );
                    else
                        three.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 4:
                    four.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        four.setImageResource( R.drawable.cross );
                    else
                        four.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 5:
                    five.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        five.setImageResource( R.drawable.cross );
                    else
                        five.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 6:
                    six.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        six.setImageResource( R.drawable.cross );
                    else
                        six.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 7:
                    seven.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        seven.setImageResource( R.drawable.cross );
                    else
                        seven.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 8:
                    eight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        eight.setImageResource( R.drawable.cross );
                    else
                        eight.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;


                case 9:
                    nine.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    if( InternalEnvironmentState.getPlayerMark( player ).equals("cross"))
                        nine.setImageResource( R.drawable.cross );
                    else
                        nine.setImageResource( R.drawable.naught );

                    InternalEnvironmentState.update( context, player, id);
                    break;

            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

    }
}
