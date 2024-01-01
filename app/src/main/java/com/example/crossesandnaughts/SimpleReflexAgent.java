package com.example.crossesandnaughts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SimpleReflexAgent {

    private Context context;
    public SimpleReflexAgent( Context context )
    {
        this.context = context;
    }

    public void play()
    {
        int id = 0;
        try
        {
            ArrayList<Integer> slots = InternalEnvironmentState.getEmptySlots();

            if( slots.size() == 0 )
            {
                return;
            }
            int index = (int) android.os.SystemClock.uptimeMillis() % slots.size();

            id = slots.get(index);

            MainActivity.unsetClickListener( context, "agent", id);
            InternalEnvironmentState.update( context, "agent", id);

            if( InternalEnvironmentState.gameOver( context ) )
            {
                String s = "";
                if( InternalEnvironmentState.userWon )
                    s += "You Win!";
                else
                    s += "You Loose!";
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
                    }
                });
            }


        }
        catch ( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }


    }
}

class GoalBasedAgent extends SimpleReflexAgent
{
    private Context context;
    public GoalBasedAgent( Context context )
    {
        super( context );
        this.context = context;
    }

    @Override
    public void play()
    {
        try
        {
            Toast.makeText( context , "Goal agent playing...invoking simple reflex agent's play() method", Toast.LENGTH_SHORT).show();
            super.play();
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
