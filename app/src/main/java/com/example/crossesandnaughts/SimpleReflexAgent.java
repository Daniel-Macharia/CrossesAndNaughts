package com.example.crossesandnaughts;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

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
            //int index = (int) Math.random( ) * slots.size();
            int index = (int) android.os.SystemClock.uptimeMillis() % slots.size();

            id = slots.get(index);

            //Toast.makeText(context, index + "Marked position " + id, Toast.LENGTH_SHORT).show();

            MainActivity.unsetClickListener( context, "agent", id);

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
