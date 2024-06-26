package com.example.navbarre.custombottomnav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.navbarre.R;
import com.example.navbarre.adapter.AdapterViewPager;
import com.example.navbarre.fragment.FragmentHome;
import com.example.navbarre.fragment.FragmentSettings;
import com.example.navbarre.fragment.FragmentHisto;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    ViewPager2 pagerMain;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);

        if (isFirstRun) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();

            startActivity(new Intent(this, PageChargement.class));
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
            boolean isDarkModeEnabled = sharedPreferences.getBoolean("isDarkModeEnabled", false);

            if (isDarkModeEnabled) {
                setTheme(R.style.Theme_NavBarre_Dark);
            } else {
                setTheme(R.style.Theme_NavBarre_Light);
            }

            setContentView(R.layout.activity_main);

            pagerMain = findViewById(R.id.pagerMain);
            bottomNav = findViewById(R.id.bottomNav);

            fragmentArrayList.add(new FragmentHome());
            fragmentArrayList.add(new FragmentHisto());
            fragmentArrayList.add(new FragmentSettings());

            AdapterViewPager adapterViewPager = new AdapterViewPager(this, fragmentArrayList);
            pagerMain.setAdapter(adapterViewPager);
            pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    switch(position){
                        case 0:
                            bottomNav.setSelectedItemId(R.id.itHome);
                            break;
                        case 1:
                            bottomNav.setSelectedItemId(R.id.itHisto);
                            break;
                        case 2:
                            bottomNav.setSelectedItemId(R.id.itSettings);
                            break;
                    }
                    super.onPageSelected(position);
                }
            });
            bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    if (item.getItemId() == R.id.itHome) {
                        pagerMain.setCurrentItem(0);
                    } else if (item.getItemId() == R.id.itHisto) {
                        pagerMain.setCurrentItem(1);
                    } else if (item.getItemId() == R.id.itSettings) {
                        pagerMain.setCurrentItem(2);
                    }

                    return true;
                }
            });
        }
    }
}

