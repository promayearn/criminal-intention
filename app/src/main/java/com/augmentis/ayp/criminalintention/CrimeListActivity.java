package com.augmentis.ayp.criminalintention;

import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {

        return new CrimeListFragment();
    }
}