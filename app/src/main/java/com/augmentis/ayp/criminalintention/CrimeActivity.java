package com.augmentis.ayp.criminalintention;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class CrimeActivity extends FragmentActivity {

    public static final String TAG = "FragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        if(f == null) {
            f = new CrimeFragment();

            fm.beginTransaction()
            .add(R.id.fragment_container, f)
            .commit();
            Log.d(TAG, "Fragment is created");
        } else {
            Log.d(TAG, "Fragment didnt create");
        }
    }
}