package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Chayanit on 7/18/2016.
 */
public class CrimePagerActivity extends AppCompatActivity {
    private ViewPager _viewPager;
    private List<Crime> _crimes;
    private UUID _crimeId;
    protected static final String CRIME_ID = "crimeActivity.crimeID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        _crimeId = (UUID) getIntent().getSerializableExtra(CRIME_ID);


        _viewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view);
        _crimes = CrimeLab.getInstance(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();

        _viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = _crimes.get(position);
                Fragment f = CrimeFragment.newInstance(crime.getId());
                return f;
            }

            @Override
            public int getCount() {
                return _crimes.size();
            }
        });

        int position = CrimeLab.getInstance(this).getCrimePositionById(_crimeId);
        _viewPager.setCurrentItem(position);

    }

    public static Intent newIntend(Context context, UUID id) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(CRIME_ID, id);
        return intent;
    }
}