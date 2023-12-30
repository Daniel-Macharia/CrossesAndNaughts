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

    public int play()
    {
        int id = 0;
        try
        {
            ArrayList<Integer> slots = InternalEnvironmentState.getEmptySlots();

            int index = (int) Math.random( ) * slots.size();

            id = slots.get(index);

            Toast.makeText(context, "Marked position " + id, Toast.LENGTH_SHORT).show();

            MainActivity.unsetClickListener( context, "agent", id);

        }catch ( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

        return id;
    }
}
