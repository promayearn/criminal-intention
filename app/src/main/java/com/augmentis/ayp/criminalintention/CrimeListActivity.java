package com.augmentis.ayp.criminalintention;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.criminalintention.model.Crime;

public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

    @Override
    protected Fragment onCreateFragment() {

        return new CrimeListFragment();
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
        } else {
            Fragment newDetailFragment = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetailFragment).commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
//        Update Lits
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        listFragment.updateUI();
    }
}