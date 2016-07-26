package com.augmentis.ayp.criminalintention;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends FragmentActivity {

    protected static final String CRIME_ID = "crimePagerActivity.crimeId";
    protected static final String CRIME_POSITION = "crimePagerActivity.crimePosition";

    private ViewPager _viewPager;
    private List<Crime> _crimes;
    private int _position;
    private UUID _crimeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        _crimeId = (UUID) getIntent().getSerializableExtra(CRIME_ID);
        _position = (int) getIntent().getExtras().get(CRIME_POSITION);

        _viewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view);
        _crimes = CrimeLab.getInstance(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();

        _viewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = _crimes.get(position);
                Fragment f = CrimeFragment.newInstance(crime.getId(), position);
                return f;
            }

            @Override
            public int getCount() {
                return _crimes.size();
            }
        });

        //set position
        int position = CrimeLab.getInstance(this).getCrimePositionById(_crimeId);
        _viewPager.setCurrentItem(position);
    }

    public static Intent newIntent(Context context, UUID id, int position) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(CRIME_ID, id);
        intent.putExtra(CRIME_POSITION, position);
        return intent;

    }

}
