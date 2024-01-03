package com.example.crossesandnaughts;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmailDevs extends AppCompatActivity {

    EditText title, message;
    Button sendBtn;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView ( R.layout.contact_developers );

        sendBtn = findViewById( R.id.send );
        title = findViewById( R.id.title );
        message= findViewById( R.id.message );

        sendBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View view )
            {
                String titleText = title.getText().toString();
                String bodyText = message.getText().toString();



                title.setText("");
                message.setText("");

                Toast.makeText(EmailDevs.this, "Title: " + titleText + "\n\nBody: " + bodyText, Toast.LENGTH_SHORT).show();
                sendEmail(titleText, bodyText);
            }
        });

    }

    private void sendEmail( String title, String body )
    {
        try {

            if( !networkIsAvailable() )
                Toast.makeText(this, "Check network connection!", Toast.LENGTH_LONG).show();

            Intent selectorIntent = new Intent( Intent.ACTION_SENDTO);
            selectorIntent.setData( Uri.parse("mailto:"));

            String mail = "ndungudanny444@gmail.com";

            final Intent emailIntent = new Intent( Intent.ACTION_SEND );
            emailIntent.putExtra( Intent.EXTRA_EMAIL, new String[]{mail});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
            emailIntent.putExtra( Intent.EXTRA_TEXT, body);
            emailIntent.setSelector( selectorIntent );

            startActivity( Intent.createChooser( emailIntent, "Send Email") );
        }catch( Exception e )
        {
            Toast.makeText(this, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean networkIsAvailable()
    {

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE );

            Network net = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                net = cm.getActiveNetwork();
            }

            if( net != null )
            {
                NetworkCapabilities netCaps = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    netCaps = cm.getNetworkCapabilities( net );
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if( netCaps.hasCapability( NetworkCapabilities.NET_CAPABILITY_VALIDATED ) )
                        return true;
                }

            }
            else
            {
                NetworkInfo info = cm.getActiveNetworkInfo();

                if( info != null )
                {
                    if( info.isAvailable() )
                        return true;
                }
            }
        }catch( Exception e )
        {
            Toast.makeText(this, "Error: Network Error\n" + e, Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
