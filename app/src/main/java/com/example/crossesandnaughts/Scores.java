package com.example.crossesandnaughts;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;

public class Scores  extends AppCompatActivity {


    private TabLayout level_tabs;
    private ViewPager viewPager;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.scores );

        try {

            level_tabs = findViewById( R.id.tab_layout );
            viewPager = findViewById( R.id.view_pager );

            TabLayout.Tab tabEasy = level_tabs.newTab();
            TabLayout.Tab tabNormal = level_tabs.newTab();
            TabLayout.Tab tabHard = level_tabs.newTab();

            tabEasy.setText("Easy");
            tabNormal.setText("Normal");
            tabHard.setText("Hard");

            level_tabs.addTab( tabEasy);
            level_tabs.addTab( tabNormal );
            level_tabs.addTab( tabHard );

            PagerAdapter adapter = new PagerAdapter( getSupportFragmentManager(), level_tabs.getTabCount() );
            viewPager.setAdapter( adapter );

            viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( level_tabs ) );

            level_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    switch( tab.getPosition() )
                    {
                        case 0:
                            viewPager.setCurrentItem( 0 );
                            break;
                        case 1:
                            viewPager.setCurrentItem( 1 );
                            break;
                        case 2:
                            viewPager.setCurrentItem( 2 );
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        }catch( Exception e )
        {
            Toast.makeText(this, "Error from Application: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
