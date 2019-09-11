package com.mohamed.ezz.nearestmasjids.activites;

import android.os.Bundle;

import com.mohamed.ezz.nearestmasjids.R;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragmentContainer) != null) {
            if (savedInstanceState != null)
                return;
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, mainFragment).commit();
        }

    }
}
