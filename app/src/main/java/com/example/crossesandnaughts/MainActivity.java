package com.example.crossesandnaughts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static SimpleReflexAgent agent;
    private static ImageView one, two, three, four, five, six, seven, eight, nine;
    //private Map<Integer, String> marks;

    private ImageView menu;

    private static Executor executor;

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
                    createMenus();
                }
            });

            //initMap();
            executor = Executors.newCachedThreadPool();

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

    private void createMenus()
    {
        Toast.makeText(this, "Clicked menus", Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText( context, "Clicked one", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 1 );
                    agent.play();
                }
            });

            two.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked two", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 2 );
                    agent.play();
                }
            });

            three.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked three", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 3 );
                    agent.play();
                }
            });

            four.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked four", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 4 );
                    agent.play();
                }
            });

            five.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked five", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 5 );
                    agent.play();
                }
            });

            six.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked six", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 6 );
                    agent.play();
                }
            });

            seven.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked seven", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 7 );
                    agent.play();
                }
            });

            eight.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked eight", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 8 );
                    agent.play();
                }
            });

            nine.setOnClickListener( new View.OnClickListener()
            {
                @Override
                public void onClick( View v )
                {
                    //Toast.makeText( context, "Clicked nine", Toast.LENGTH_SHORT).show();
                    unsetClickListener( context, "user", 9 );
                    agent.play();
                }
            });
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }

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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                     //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
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

                    //executor.execute(new Runnable() {
                    //    @Override
                    //    public void run() {
                    InternalEnvironmentState.update( context, player, id);
                    //   }
                    //});
                    break;

            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

    }
}
