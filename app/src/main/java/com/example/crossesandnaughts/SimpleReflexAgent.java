package com.example.crossesandnaughts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;
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

            updateAndCheckIfGameIsOver( context, id);


        }
        catch ( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

    }

    public void updateAndCheckIfGameIsOver( Context context, int id )
    {
        try
        {
            MainActivity.unsetClickListener( context, "agent", id);
            InternalEnvironmentState.update( context, "agent", id);

            if( InternalEnvironmentState.gameOver( context ) )
            {
                String s = "Game Over!\n\n";
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
                tv.setText( s );
                d.setContentView( tv );
                d.show();

                d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog){
                        InternalEnvironmentState.refresh();
                    }
                });
            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from application: " + e , Toast.LENGTH_SHORT).show();
        }
    }
}

class Possible
{
    public int id1;
    public int id2;

    public Possible( int id1, int id2 )
    {
        this.id1 = id1;
        this.id2 = id2;
    }
}

class GoalBasedAgent extends SimpleReflexAgent
{
    private Context context;
    private ArrayList<Possible> possibleWins;
    public GoalBasedAgent( Context context )
    {
        super( context );
        this.context = context;
    }

    public boolean isBlank( int id )
    {
        if( id < 1 )
            return false;

        Position p = InternalEnvironmentState.getPosition( id );

        if( InternalEnvironmentState.marks[ p.getRow()][p.getCol()].equals("blank") )
            return true;

        return false;
    }

    public boolean areAligned( int id1, int id2 )
    {
        Position p1 = InternalEnvironmentState.getPosition( id1 );
        Position p2 = InternalEnvironmentState.getPosition( id2 );

        if( p1.getRow() == p2.getRow() )
            return true;
        else if( p1.getCol() == p2.getCol() )
            return true;
        else if( ( p2.getRow() - p1.getRow() ) == ( p2.getCol() - p1.getCol() ) )
            return true;
        else if( ( p2.getRow() - p1.getRow() ) == ( p1.getCol() - p2.getCol() ) )
            return true;

        return false;
    }

    public int getThird( int id1, int id2 )
    {
        Position p1 = InternalEnvironmentState.getPosition( id1 );
        Position p2 = InternalEnvironmentState.getPosition( id2 );

        if( p1.getRow() == p2.getRow() )
        {
            if( Math.abs(p1.getCol() - p2.getCol()) == 1 )
            {
                if( p1.getCol() < p2.getCol() && p2.getCol() < 2)
                {
                    return InternalEnvironmentState.getId( new Position( p1.getRow(), 2));
                }
                else if( p2.getCol() < p1.getCol() && p1.getCol() < 2)
                    return InternalEnvironmentState.getId( new Position( p1.getRow(), 2));
                else
                    return InternalEnvironmentState.getId( new Position( p1.getRow(), 0));
            }
            else
                return InternalEnvironmentState.getId( new Position( p1.getRow(), 1));
        }
        else if( p1.getCol() == p2.getCol() )
        {
            if( Math.abs( p1.getRow() - p2.getRow() ) == 1)
            {
                if( p1.getRow() < p2.getRow() && p2.getRow() < 2)
                    return InternalEnvironmentState.getId( new Position( 2, p1.getCol()));
                else if( p2.getRow() < p1.getRow() && p1.getRow() < 2 )
                    return InternalEnvironmentState.getId( new Position( 2, p1.getCol()));
                else
                    return InternalEnvironmentState.getId( new Position( 0, p1.getCol()));
            }
            else
                return InternalEnvironmentState.getId( new Position( 1, p1.getCol()));
        }
        else if( p1.getRow() - p2.getRow() == p1.getCol() - p2.getCol() )
        {
            if( Math.abs( p1.getRow() - p2.getRow()) == 1 )
            {
                if( p1.getCol() < p2.getCol() && p2.getCol() < 2)
                    return InternalEnvironmentState.getId( new Position( 2, 2) );
                else if( p2.getCol() < p1.getCol() && p1.getCol() < 2 )
                    return InternalEnvironmentState.getId( new Position( 2, 2) );
                else
                    return InternalEnvironmentState.getId( new Position( 0, 0) );
            }
            else
                return InternalEnvironmentState.getId( new Position( 1, 1) );
        }
        else if( p1.getRow() - p2.getRow() == p2.getCol() - p1.getCol() )
        {
            if( Math.abs( p1.getRow() - p2.getRow()) == 1 )
            {
                if( p1.getCol() < p2.getCol() && p2.getCol() < 2)
                    return InternalEnvironmentState.getId( new Position( 0, 2) );
                else if( p2.getCol() < p1.getCol() && p1.getCol() < 2 )
                    return InternalEnvironmentState.getId( new Position( 0, 2) );
                else
                    return InternalEnvironmentState.getId( new Position( 2, 0) );
            }
            else
                return InternalEnvironmentState.getId( new Position( 1, 1) );
        }

        return -1;
    }
    public ArrayList<Possible> getPossibleWins(String player)
    {
        ArrayList<Possible> possible_wins = new ArrayList<Possible>();
        try
        {
            ArrayList<Integer> list = new ArrayList<>();

            for( int r = 0; r < InternalEnvironmentState.marks.length; r++)
            {
                for( int c = 0; c < InternalEnvironmentState.marks[r].length; c++)
                {
                    if( InternalEnvironmentState.marks[r][c].equals( InternalEnvironmentState.getPlayerMark(player) ) )
                        list.add( InternalEnvironmentState.getId( new Position( r, c) ) );

                }
            }

            for( int id1 : list )
            {
                for( int id2 : list )
                {
                    if( id1 == id2 )
                        continue;

                    if( areAligned( id1, id2 ) && isBlank( getThird( id1, id2 ) ) )
                    {
                        possible_wins.add( new Possible( id1, id2) );
                    }

                }
            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from App: " + e, Toast.LENGTH_SHORT).show();
        }

        return possible_wins;

    }

    public ArrayList<Integer> getMarks()
    {
        ArrayList<Integer> mark = new ArrayList<>();
        String agentMark = InternalEnvironmentState.getPlayerMark( "agent" );
        for( int r = 0; r < InternalEnvironmentState.marks.length; r++)
        {
            for( int c = 0; c < InternalEnvironmentState.marks[r].length; c++)
            {
                if( InternalEnvironmentState.marks[r][c].equals( agentMark ) )
                {
                    mark.add( InternalEnvironmentState.getId( new Position( r, c ) ) );
                }
            }
        }

        return mark;
    }

    public int getNext( int firstId )
    {
        for( int i = 1; i <= 9; i++)
        {
            if( firstId == i )
                continue;

            int third = getThird( firstId, i);

            if( isBlank(i) && areAligned( firstId, i ) && isBlank( third ) )
            {
                return third;
            }
        }

        return -1;
    }

    @Override
    public void play()
    {
        try
        {
            possibleWins = getPossibleWins("agent");
            ArrayList<Integer> agentMarks = getMarks();

            if( possibleWins.size() > 0 )
            {
                int id = -1;
                for( Possible p : possibleWins )
                {
                    id = getThird( p.id1, p.id2 );
                    if( isBlank( id ) )
                    {
                        super.updateAndCheckIfGameIsOver( context, id);
                        return;
                    }
                }


            }
            else if( agentMarks.size() > 0 )
            {
                int id = -1;

                for( int i : agentMarks )
                {
                    id = getNext( i );
                    if( isBlank( id ) )
                    {
                        super.updateAndCheckIfGameIsOver( context, id);
                        return;
                    }
                }

            }

            super.play();


        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}



class UtilityAgent extends GoalBasedAgent
{
    private Context context;
    public UtilityAgent( Context context )
    {
        super( context );
        this.context = context;
    }

    @Override
    public void play()
    {
        try
        {
            //Toast.makeText( context , "Utility agent playing...invoking goal agent's play() method", Toast.LENGTH_SHORT).show();

            ArrayList<Possible> opponentPossibleWins = super.getPossibleWins("user");
            ArrayList<Possible> myPossibleWins = super.getPossibleWins("agent");

            if(myPossibleWins.size() > 0 )
            {
                for( Possible p : myPossibleWins )
                {
                    int id = super.getThird( p.id1, p.id2 );

                    if( super.isBlank( id ) )
                    {
                        super.updateAndCheckIfGameIsOver( context, id);
                        return;
                    }
                }
            }
            else
                if( opponentPossibleWins.size() > 0 )
            {
                for( Possible p : opponentPossibleWins )
                {
                    int id = super.getThird( p.id1, p.id2 );

                    if( super.isBlank( id ) )
                    {
                        super.updateAndCheckIfGameIsOver( context, id);
                        return;
                    }
                }
            }

            super.play();
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
