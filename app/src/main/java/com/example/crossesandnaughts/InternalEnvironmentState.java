package com.example.crossesandnaughts;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import java.util.ArrayList;

public class InternalEnvironmentState {
    public static Context context;

    private static MyMap opponent = new MyMap();

    public static boolean userWon = false, agentWon = false;

    public static String[][] marks = new String[3][3];

    public static void init()
    {
       try
       {
           for( int r = 0; r < 3; r++)
           {
               for( int c = 0; c < 3; c++)
               {
                   marks[r][c] = new String("blank");
               }
           }

           userWon = false;
           agentWon = false;
       }catch( Exception e )
       {
           Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
       }
    }

    public static int getId( Position pos)
    {
        int id = 0;

        id = 3 * pos.getRow();
        id += pos.getCol() + 1;

        return id;
    }

    public static Position getPosition( int id )
    {
        int r = id / 3;
        int c = id % 3;

        if( c == 0 )
        {
            r--;
            c = 2;
        }
        else
        {
            c--;
        }

        return new Position( r, c);
    }

    public static void setUserMark(String userMark)
    {
        try
        {
            String agentMark = (userMark.equals("cross") ? "naught" : "cross");
            opponent.put("user", userMark);
            opponent.put("agent", agentMark);
        }catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }
    }

    public static String getPlayerMark( String player)
    {
        return opponent.get( player );
    }

    public static void update(Context context, String player, int id )
    {
        try
        {
            Position point = getPosition( id );

            marks[ point.getRow()][point.getCol()] = opponent.get( player );

            gameOver( context );
        }catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<Integer> getEmptySlots()
    {
        ArrayList<Integer> slots = new ArrayList<>();

        try
        {
            for( int r = 0; r < marks.length; r++ )
            {
                for( int c = 0; c < marks[r].length; c++)
                {
                    if( marks[r][c].equals("blank") )
                    {
                        slots.add( getId( new Position( r, c ) ) );
                    }
                }
            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

        return slots;
    }

    public static boolean isOver()
    {
        try
        {
            int userForwardDiagonal = 0, userReverseDiagonal = 0;
            int agentForwardDiagonal = 0, agentReverseDiagonal = 0;

            for( int r = 0, invertR = 2; r < marks.length; r++, invertR--)
            {
                int userHorizontalMarks = 0, userVerticalMarks = 0;
                int agentHorizontalMarks = 0, agentVerticalMarks = 0;
                for( int c = 0; c < marks[r].length; c++)
                {
                    if( marks[r][c].equals(opponent.get("user")) )
                    {
                        userHorizontalMarks++;
                    }
                    else if( marks[r][c].equals( opponent.get("agent")) )
                    {
                        agentHorizontalMarks++;
                    }

                    if( marks[c][r].equals(opponent.get("user")) )
                    {
                        userVerticalMarks++;
                    }
                    else if( marks[c][r].equals( opponent.get("agent")) )
                    {
                        agentVerticalMarks++;
                    }

                    if( r == c )
                    {
                        if( marks[r][c].equals( opponent.get("user")) )
                        {
                            userForwardDiagonal++;
                        }
                        else if( marks[r][c].equals( opponent.get("agent")) )
                        {
                            agentForwardDiagonal++;
                        }
                    }

                    if( invertR == c )
                    {
                        if( marks[r][c].equals( opponent.get("user")) )
                        {
                            userReverseDiagonal++;
                        }
                        else if( marks[r][c].equals( opponent.get("agent")) )
                        {
                            agentReverseDiagonal++;
                        }
                    }

                }

                if( userHorizontalMarks == 3 || userVerticalMarks == 3 )
                {
                    userWon = true;
                    return true;
                }

                if( agentHorizontalMarks == 3 || agentVerticalMarks == 3 )
                {
                    agentWon = true;
                    return true;
                }
            }


            if( userForwardDiagonal == 3 || userReverseDiagonal == 3 )
            {
                userWon = true;
                return true;
            }

            if( agentForwardDiagonal == 3 || agentReverseDiagonal == 3)
            {
                agentWon = true;
                return true;
            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error From Application: "+ e, Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public static boolean gameOver( Context context )
    {
        try
        {
            if( isOver() || getEmptySlots().size() == 0 )
            {
                return true;
            }
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public static void refresh()
    {
        try
        {
            init();

            MainActivity.resetGame( context );

            MainActivity.setClickListeners( context );
        }catch( Exception e )
        {
            Toast.makeText(context, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }

}

class Position
{
    private int row, col;

    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow(){ return this.row;}
    public int getCol(){ return this.col;}
    public void setRow( int row ){ this.row = row; }
    public void setCol( int col ){ this.col = col; }
}

class MyMap
{
    private ArrayList<Pair<String, String>> map;
    public MyMap()
    {
        map = new ArrayList<>();
    }

    public void put( String key, String value)
    {
        map.add( new Pair<>( new String( key), new String( value) ) );
    }

    public String get( String key )
    {
        for( Pair<String, String> p : map )
        {
            if( p.first.equals( key ) )
                return p.second;
        }

        return null;
    }
}
